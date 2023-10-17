/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.habit.app

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.Node
import com.google.android.gms.wearable.Wearable
import com.habit.presentation.main.HabitApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okhttp3.internal.wait


private const val TAG = "MainActivity"
@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private lateinit var navController: NavHostController
    private var transcriptionNodeId: String? = null

    // 모바일-워치 간 연결
    private lateinit var capabilityClient: CapabilityClient
    private lateinit var messageClient: MessageClient
    private lateinit var dataClient: DataClient

    private val clientDataViewModel by viewModels<ClientDataViewModel>()
    private lateinit var dataLayerViewModelFactory : DataLayerViewModelFactory
    private lateinit var dataLayerViewModel : DataLayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        var pendingNavigation = true

        super.onCreate(savedInstanceState)

        // 모바일-워치 간 연결
        capabilityClient = Wearable.getCapabilityClient(this)
        messageClient = Wearable.getMessageClient(this)
        dataClient = Wearable.getDataClient(this)

        // watchLandingViewModel 생성
        dataLayerViewModelFactory = DataLayerViewModelFactory(messageClient, capabilityClient, this.application)
        dataLayerViewModel = ViewModelProvider(this@MainActivity, dataLayerViewModelFactory)[DataLayerViewModel::class.java]
        
        setContent {
            navController = rememberSwipeDismissableNavController()

            HabitApp(
                dataLayerViewModel,
                navController,
                onFinishActivity = { this.finish() }
            )

//            LaunchedEffect(Unit) {
//                prepareIfNoExercise()
//                pendingNavigation = false
//            }
        }
    }
//
//    private suspend fun getCapabilitiesForReachableNodes(): Map<Node, Set<String>> =
//        capabilityClient.getAllCapabilities(CapabilityClient.FILTER_REACHABLE)
//            .await()
//            // Pair the list of all reachable nodes with their capabilities
//            .flatMap { (capability, capabilityInfo) ->
//                capabilityInfo.nodes.map { it to capability }
//            }
//            // Group the pairs by the nodes
//            .groupBy(
//                keySelector = { it.first },
//                valueTransform = { it.second }
//            )
//            // Transform the capability list for each node into a set
//            .mapValues { it.value.toSet() }
//    private fun setupVoiceTranscription() {
//        val capabilityInfo: CapabilityInfo = Tasks.await(
//            Wearable.getCapabilityClient(this)
//                .getCapability(
//                    CAPABILITY_NAME,
//                    CapabilityClient.FILTER_REACHABLE
//                )
//        )
//        // capabilityInfo has the reachable nodes with the transcription capability
//        updateTranscriptionCapability(capabilityInfo)
//    }
//
//    private fun updateTranscriptionCapability(capabilityInfo: CapabilityInfo) {
//        transcriptionNodeId = pickBestNodeId(capabilityInfo.nodes)
//    }
//
//    private fun pickBestNodeId(nodes: Set<Node>): String? {
//        // Find a nearby node or pick one arbitrarily.
//        return nodes.firstOrNull { it.isNearby }?.id ?: nodes.firstOrNull()?.id
//    }

    override fun onResume() {
        super.onResume()
        // dataClient.addListener()
    }
//    private suspend fun prepareIfNoExercise() {
//        /** Check if we have an active exercise. If true, set our destination as the
//         * Exercise Screen. If false, route to preparing a new exercise. **/
//        val isRegularLaunch =
//            navController.currentDestination?.route == Screen.Exercise.route
//        if (isRegularLaunch && !exerciseViewModel.isExerciseInProgress()) {
//            navController.navigate(Screen.PreparingExercise.route)
//        }
//    }
}