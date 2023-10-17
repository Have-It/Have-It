package com.meta.member.service;

import com.meta.member.dto.response.SelfyResponseDto;
import com.meta.member.entity.Member;
import com.meta.member.entity.Photo;
import com.meta.member.exception.BusinessException;
import com.meta.member.exception.enums.ErrorCode;
import com.meta.member.repository.MemberRepository;
import com.meta.member.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import javax.persistence.EntityManager;

import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.net.URL;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoService {
    private final MemberRepository memberRepository;
    private final EntityManager entityManager;
    private final PhotoRepository photoRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.credentials.access_key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret_key}")
    private String secretKey;

    private final S3Client s3 = S3Client.builder()
            .region(Region.AP_NORTHEAST_2)
            .credentialsProvider(() -> AwsBasicCredentials.create(accessKey, secretKey))
            .build();

    @Transactional
    public String uploadPhoto(Long memberId, String base64EncodedImage, String bodyType) {
        byte[] photoBytes = Base64.getDecoder().decode(base64EncodedImage);
        String folderName = bodyType.equals("fullBody") ? "home" : "setting";
        String objectKey = folderName + "/" + memberId + ".png";

        try {
            s3.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build());
            System.out.println("Existing photo deleted.");
        } catch (S3Exception e) {
            throw new BusinessException(ErrorCode.FAIL_SAVE_IMAGE);
        }

        s3.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(objectKey)
                        .build(),
                RequestBody.fromByteBuffer(ByteBuffer.wrap(photoBytes)));

        return s3.utilities().getUrl(GetUrlRequest.builder()
                        .bucket(bucketName)
                        .key(objectKey)
                        .build())
                .toString();
    }
//
//    @Transactional
//    public String getPhotoUrl(String memberId, String bodyType) {
//        return s3.utilities().getUrl(GetUrlRequest.builder()
//                        .bucket(bucketName)
//                        .key((bodyType.equals("fullBody") ? "home/" : "setting/") + memberId + ".jpg")
//                        .build())
//                .toString();
//    }

    @Transactional
    public void updateProfileImage(Long memberId, String profileImageUrl) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            member.setProfileImage(profileImageUrl);
        } else {
            throw new BusinessException(ErrorCode.NOT_FOUND_MEMBER);
        }
    }

    @Transactional
    public void updateSettingImage(Long memberId, String settingImageUrl) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            member.setSettingImage(settingImageUrl);
        } else {
            throw new BusinessException(ErrorCode.NOT_FOUND_MEMBER);
        }
    }

    @Transactional
    public SelfyResponseDto uploadSelfy(Long memberId, String base64EncodedImage) {
        byte[] photoBytes = Base64.getDecoder().decode(base64EncodedImage);

        Photo photo = new Photo();

        Member member=memberRepository.findById(memberId).get();

        member.addPhoto(photo);

        String folderName = "photos";
        String objectKey = folderName + "/" + memberId + "/" + System.currentTimeMillis() + ".png";

        try {
            s3.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(objectKey)
                            .build(),
                    RequestBody.fromByteBuffer(ByteBuffer.wrap(photoBytes)));
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.FAIL_SAVE_IMAGE);
        }

        photo.setUrl(s3.utilities().getUrl(GetUrlRequest.builder()
                        .bucket(bucketName)
                        .key(objectKey)
                        .build())
                .toString());

        entityManager.flush();

        return new SelfyResponseDto(memberId, photo.getId(), photo.getUrl());
    }

    @Transactional(readOnly = true)
    public List<SelfyResponseDto> getAllPhotos(Long memberId) {
        List<Photo> photos = memberRepository.findById(memberId).get().getPhotos();

        return photos.stream()
                .map(photo -> new SelfyResponseDto(memberId, photo.getId(), photo.getUrl()))
                .collect(Collectors.toList());
    }

//    @Transactional
//    public void deleteSelfy(Long memberId, Long id) {
//        Optional<Photo> optionalPhoto = memberRepository.findById(memberId).get().getPhotos().stream()
//                .filter(photo -> Objects.equals(photo.getId(), id))
//                .findFirst();
//
//        if (optionalPhoto.isPresent()) {
//            Photo photo = optionalPhoto.get();
//
//            try {
//                String objectKey = extractObjectKeyFromUrl(photo.getUrl());
//
//                s3.deleteObject(DeleteObjectRequest.builder()
//                        .bucket(bucketName)
//                        .key(objectKey)
//                        .build());
//
//                // Remove the photo from the database
//                photoRepository.delete(photo);
//            } catch (MalformedURLException e) {
//                // Handle exception...
//            }
//        } else {
//            throw new BusinessException(ErrorCode.NOT_FOUND_PHOTO);
//        }
//    }
//
//    private String extractObjectKeyFromUrl(String url) throws MalformedURLException {
//        URL s3Url = new URL(url);
//        return Paths.get(s3Url.getPath()).toString();
//    }

}

