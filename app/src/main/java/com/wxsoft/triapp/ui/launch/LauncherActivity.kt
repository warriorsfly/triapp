//package com.wxsoft.triapp.ui.launch
//
//import android.Manifest
//import android.app.AlertDialog
//import android.app.DownloadManager
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.content.IntentFilter
//import android.content.pm.PackageManager
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.os.Environment
//import android.provider.Settings
//import androidx.core.app.ActivityCompat
//import androidx.databinding.DataBindingUtil
//import androidx.lifecycle.Observer
//import com.warriorsfly.core.di.ViewModelFactory
//import com.warriorsfly.core.utils.viewModelProvider
//import com.wxsoft.triapp.R
//import com.wxsoft.triapp.databinding.ActivityLauncherBinding
//import com.wxsoft.triapp.ui.common.BaseActivity
//import com.wxsoft.triapp.ui.login.LoginActivity
//import com.wxsoft.triapp.ui.main.MainActivity
//import io.reactivex.Observable
//import io.reactivex.Single
//import io.reactivex.disposables.CompositeDisposable
//import java.io.File
//import java.util.concurrent.TimeUnit
//import javax.inject.Inject
//
//
//class LauncherActivity : BaseActivity(){
//
//    private val disposable= CompositeDisposable()
//    private val downloadManager:DownloadManager by lazy {
//        getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//    }
//
//    private var downloadId:Long=0L
//
//    private var dialog: UpgradeDialog?=null
//    @Inject
//    lateinit var factory: ViewModelFactory
//
//    private lateinit var viewModel: LauncherViewModel
//    private lateinit var file: File
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        viewModel = viewModelProvider(factory)
//        DataBindingUtil.setContentView<ActivityLauncherBinding>(this, R.layout.activity_launcher).apply {
//            lifecycleOwner=this@LauncherActivity
//            viewModel=this@LauncherActivity.viewModel
//        }
//
//        viewModel.version.observe(this, Observer {
//
//            if (it.changing) {
//                checkUpgradePermission()
//            }
//        })
//
//        viewModel.success.observe(this, Observer {
//
//            val intent = Intent(this,if (it) MainActivity::class.java  else LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//        })
//
//        viewModel.update.observe(this, Observer {
//            viewModel.version.value?.let(::download)
//        })
//
//
//        checkStoragePermission()
//    }
//
//
//    private fun error(throwable: Throwable){}
//    private fun doVersion(code:Long){
//
//        viewModel.local=code
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        disposable.clear()
//        if(completeReceiver!=null){
//            unregisterReceiver(completeReceiver)
//            completeReceiver=null
//        }
//    }
//
//    private fun getLocalVersion():Long{
//        return if (Build.VERSION.SDK_INT <Build.VERSION_CODES.P) {
//            packageManager.getPackageInfo(packageName,0).versionCode.toLong()
//
//        }else{
//            packageManager.getPackageInfo(packageName,0).longVersionCode
//        }
//    }
//
//    private fun showUpdateDialog(){
//        if(dialog==null)
//            dialog = UpgradeDialog()
//        dialog?.show(supportFragmentManager, UpgradeDialog.DIALOG_UPGRADE)
//    }
//    private fun checkStoragePermission() {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                UPGRADE_PERMISSION_REQUEST
//            )
//
//        } else {
//            checkUpdate()
//        }
//    }
//    private fun checkUpdate(){
//        disposable.add(Single.fromCallable { getLocalVersion() }.subscribe(::doVersion, ::error))
//    }
//
//    private fun checkUpgradePermission(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            if (packageManager.canRequestPackageInstalls()) {
//                showUpdateDialog()
//            } else {
//                AlertDialog.Builder(this,R.style.Theme_FCare_Dialog)
//                    .setTitle("提示")
//                    .setMessage("请在设置允许安装未知应用的列表里找到无限急救，点击，选择允许")
//                    .setPositiveButton("去设置") { _, _ ->
//                        val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
//                        startActivityForResult(intent, UPGRADE_PERMISSION_SETTING)
//                    }
//                    .show()
//            }
//        }else{
//            showUpdateDialog()
//        }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            UPGRADE_PERMISSION_REQUEST -> {
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    checkUpdate()
//                } else {
//                    checkStoragePermission()
//                }
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        when (requestCode) {
//            UPGRADE_PERMISSION_SETTING->{
//                checkUpgradePermission()
//            }
//        }
//    }
//
//
//    private var completeReceiver: CompleteReceiver? = null
//
//
//    private fun download(version:Version){
//
//        val request = DownloadManager.Request(Uri.parse("""${BuildConfig.ENDPOINT}${version.url}""")).apply {
//
//            file = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "无限急救.apk")
//            setDestinationUri(Uri.fromFile(file))
//        }
//        downloadId=downloadManager.enqueue(request)
//
//        disposable.add(Observable.interval(2,TimeUnit.SECONDS).subscribe{queryState()})
//        completeReceiver = CompleteReceiver()
//        registerReceiver(
//            completeReceiver,
//            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
//        )
//    }
//
//    private fun queryState(){
//        downloadManager.query(DownloadManager.Query().apply {
//            setFilterById(downloadId)
//        }).use{
//            if(it.moveToFirst()){
//                viewModel.processPercent.set(it.getInt(it.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))/(1024*1024))
//                viewModel.processTotal.set(it.getInt(it.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))/(1024*1024))
//            }
//        }
//
//    }
//
//
//    inner class CompleteReceiver : BroadcastReceiver() {
//
//        override fun onReceive(context: Context, intent: Intent) {
//            try {
//                val uri = downloadManager.getUriForDownloadedFile(downloadId)
//                if(uri==null){
//                    viewModel.messageAction.value=Event("下载失败")
//                }else {
//
//                    disposable.clear()
//                    viewModel.processPercent.set(0)
//                    viewModel.processTotal.set(0)
//                    install(uri)
//                }
//            }catch (e:Exception){
//                viewModel.messageAction.value=Event(e.message?:"")
//            }
//        }
//
//        private fun install(uri:Uri) {
//
//            val intent = Intent(Intent.ACTION_VIEW).apply {
//                setDataAndType(uri, "application/vnd.android.package-archive")
//                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)//4.0以上系统弹出安装成功打开界面
//                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            }
//            startActivity(intent)
//
//        }
//    }
//
//}
