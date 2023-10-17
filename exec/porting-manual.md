# 📜Porting Manual


## 📝목차
1) [시스템 환경 및 버전정보](#1-시스템-환경-및-버전정보)
2) [포트 정보](#2-포트-정보)
3) [서버 접속](#3-서버-접속)
4) [빌드 및 배포](#4-빌드-및-배포)
5) [Jenkins](#5-Jenkins)
6) [NGINX](#6-NGINX)
7) [React](#7-React) 
8) [APK](#8-APK) 



## 1. ⚙시스템 환경 및 버전정보

- JVM : JDK 11
- Web FrontendIDE : Visual Studio Code 1.82.3
- Unity Frontend IDE :  Visual Studio 2019 : v16.11.29
- Android Frnotend IDE : Android Studio Flamingo 2022.2.1 Patch 2
- Backend IDE : Intellij Ultimate 2023.1.3
- Backend Framework : SpringBoot 2.7.13
- Android : Gradle 8.0, Kotlin 1.8.20
- React : React 18.2.0
- Photon Cloud : Photon Pun v2.19 , Photon lib v4.1.4.3
- Unity :  Unity Editor v2021.3.29f1
- CI CD : AWS EC2 instance - ubuntu 20.04, NGINX 1.18.0
- DB : MySQL 8.0.33, Redis 7.2.1


<br>

## 2. 🔌포트 정보

| Port | 이름                          |
|:-----|:----------------------------|
| 8761 | Eureka    |
| 8000 | API Gateway    |
| 8011 | Member Service                       |
| 8012 | Data Service                       |
| 8013 | Mission Service                       |
| 8014 | Store Service                       |
| 8015 | Character Service                       |
| 8016 | Game Service                       |
| 3306 | MySQL                       |
| 8080 | Jenkins Docker Container    |
| 6379 | Redis Docker Container      |
| 3000 | Web Frontend      |

<br>

## 3. 💻 서버 접속



3.1. 포트 개방
```
$ sudo ufw allow {portnumer} 
$ sudo ufw status
```


3.2. 도커 설치 후 실행
```
$ sudo apt update
$ sudo apt-get install docker-ce docker-ce-cli containerd.io
$ sudo systemctl start docker
```

3.3. 컨테이너 실행
```
# MySQL
$ sudo docker run --name mysql-container -p 3306:3306 -e MYSQL_ROOT_PASSWORD={PASSWORD} -d mysql
# Redis
$ sudo docker run --name my-redis -p 6379:6379 -d redis
# Jenkins
$ sudo docker run -d \
-u root \
-p 8080:8080 \
--name=jenkins \
-v /home/ubuntu/docker/jenkins-data:/var/jenkins_home \
-v /var/run/docker.sock:/var/run/docker.sock \
-v /usr/bin/docker:/usr/bin/docker \
jenkins/jenkins
```

<br>

## 4. 🚀 빌드 및 배포

4.1. Dockerfile 작성

```dockerfile
# Dockerfile
FROM adoptopenjdk/openjdk11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

4.2. 로컬에서 도커 이미지 빌드 및 푸시
```
docker build -t {사용자명}/{이미지파일명} .
docker push {사용자명}/{이미지파일명}
```



4.3. EC2에서 도커 이미지 풀 및 컨테이너 실행
```
sudo docker pull {사용자명}/{이미지파일명}
sudo docker run -d -p {서비스 포트넘버}:{서비스 포트넘버} --network=mynetwork  -e PASSWORD={패스워드} -e TZ=Asia/Seoul --name {컨테이너 이름} {사용자명}/{이미지파일명}
```

<br>



## 5. 🏭Jenkins

### 5.1. Jenkins Pipeline script
```
pipeline {
    agent any
    environment {
        DOCKER_IMAGE = '{docker image name}'
        PASSWORD = '{password}'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'feature-b/{service name}', url: '{git lab url}', credentialsId: '{gitlab credentilasId}'
            }
        }
         stage('Build Project') {
            steps {
                dir('Server/{service name}') {
                    sh "chmod +x gradlew"
                    sh "./gradlew build" 
                } 
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build --build-arg JAR_FILE=build/libs/{service name}-0.0.1-SNAPSHOT.jar -t ${DOCKER_IMAGE} ./Server/{service name}"
                }
            }
        }
        stage('Push Docker Image'){
            steps{
                script{
                    withCredentials([usernamePassword(credentialsId: '{credentialsId}', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) { 
                        sh("echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin")
                        sh "docker push ${DOCKER_IMAGE}"
                    }   
                }   
            }  
        } 
        stage('Deploy'){
            steps{
                sshagent(['ubuntu']) {
                    sh """
                        ssh -o StrictHostKeyChecking=no {ip, username} "sudo docker pull ${DOCKER_IMAGE}"
                        ssh -o StrictHostKeyChecking=no {ip, username} "sudo docker rm -f {service name} || true"
                        ssh -o StrictHostKeyChecking=no {ip, username} "sudo docker run -d -p {service port number}:{service port number} --network=mynetwork  -e PASSWORD=${PASSWORD} -e TZ=Asia/Seoul --name {docker container name} ${DOCKER_IMAGE}"
                    """
                }   
            } 
        }
        stage('Clean up'){
             steps{
                 script{
                     sh("docker rmi \$(docker images | grep '<none>' | awk '{ print \$3 }') || true")
                 }
             }
         }
    }
}
```

 


## 6. 🌐NGINX

6.1. Nginx 설치
```
# Nginx 설치
$ sudo apt-get install nginx

# NGINX 설정파일 작성
$ sudo nano /etc/nginx/sites-available/myproxy

# myproxy 로 심볼릭 링크 생성
$ sudo ln -s /etc/nginx/sites-available/myproxy /etc/nginx/sites-enabled/

# default 서버 블록 비활성화 (삭제하지 않고)
$ sudo rm /etc/nginx/sites-enabled/default

# 변경 사항 적용을 위해 Nginx 재시작
$ sudo systemctl restart nginx
 
# Nginx 구성 테스트
$ sudo nginx -t 

```

6.2. 설정파일
```
##

server {
    listen 80;
    listen [::]:80;
    server_name j9d201.p.ssafy.io;

    location / {
        return 301 https://$host$request_uri;
    }
}

server {
    listen [::]:443 ssl ipv6only=on; # managed by Certbot
    listen 443 ssl; # managed by Certbot
    server_name j9d201.p.ssafy.io; # managed by Certbot

    # SSL settings...
    ssl_certificate /etc/letsencrypt/live/j9d201.p.ssafy.io/fullchain.pem; # managed by Certbot
    ssl_certificate_key /etc/letsencrypt/live/j9d201.p.ssafy.io/privkey.pem; # managed by Certbot
    include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot
    ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem; # managed by Certbot

    location /meta {
        proxy_pass http://j9d201.p.ssafy.io:3000/meta;
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header X-Forwarded-Proto $scheme;
    }    

    location / {
        proxy_pass http://j9d201.p.ssafy.io:8000;
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header X-Forwarded-Proto $scheme;
    }
}

```

## 7. 💻 React
7.1 react 실행문
```python
npm i
npm start
```

<br>

## 8. 📱APK
```
# Have-It 설치 메뉴얼

## 스마트폰 앱, 워치 앱 파일 다운로드 링크

하단의 두 링크에서 파일을 각각 다운로드 해주세요

- 워치 앱 링크

[HaveItWearApp - Google Drive](https://drive.google.com/drive/folders/1e3WVbC3_7800H2EJOAXEG8VuEfdx51Vw?usp=sharing)

- 스마트폰 앱 링크

[HaveItPhoneApp - Google Drive](https://drive.google.com/drive/folders/1aJ5eMxbetBt0sOHbNKLobzNYdwwaC2Hl?usp=sharing)

```