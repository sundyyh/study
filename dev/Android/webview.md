
###WebView选择图片和视频点击无反应

```kotlin

 protected fun initWebView() {
        with(mWebView) {
            webViewClient = mWebClient
            webChromeClient = mWebChromeClient

            isVerticalScrollBarEnabled = false
            isHorizontalScrollBarEnabled = false
            //屏蔽长按事件
            setOnLongClickListener {
                true
            }
            val webSettings = mWebView.settings
            //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
            webSettings.javaScriptEnabled = true
            //设置自适应屏幕，两者合用
            webSettings.useWideViewPort = true //将图片调整到适合webview的大小
            webSettings.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
            webSettings.builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
            webSettings.displayZoomControls = true //隐藏原生的缩放控件
            webSettings.allowFileAccess = true //设置可以访问文件
            webSettings.textZoom = 100
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //处理http和https混合加载
                webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
            webSettings.cacheMode = WebSettings.LOAD_NO_CACHE //设置不缓存
            webSettings.javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
            webSettings.loadsImagesAutomatically = true //支持自动加载图片
            webSettings.defaultTextEncodingName = "utf-8" //设置编码格式
            webSettings.domStorageEnabled = true
            webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            webSettings.loadWithOverviewMode = true // 缩放至屏幕的大小
            webSettings.useWideViewPort = true  //自适应屏幕
            webSettings.displayZoomControls = true //隐藏缩放控件
            webSettings.setSupportZoom(true)  //支持缩放
        }
    }
    
    private val mWebChromeClient = object : WebChromeClient() {
    
            override fun onProgressChanged(webView: WebView?, newProgress: Int) {
                super.onProgressChanged(webView, newProgress)
                onClientActionListener?.onProgressChanged(newProgress)
            }
    
            /**
             * 文件选择方法
             * @param webView WebView
             * @param filePathCallback ValueCallback<Array<Uri>> 选择后回调
             * @param fileChooserParams FileChooserParams 筛选参数
             * @return Boolean
             */
            override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?, fileChooserParams: FileChooserParams?): Boolean {
                this@GoWebActivity.filePathCallback = filePathCallback
                val target = Intent(Intent.ACTION_GET_CONTENT)
                target.addCategory(Intent.CATEGORY_OPENABLE)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    target.type = fileChooserParams?.createIntent()?.type
                    target.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, fileChooserParams?.mode == FileChooserParams.MODE_OPEN_MULTIPLE)
                } else {
                    target.type = "*/*"
                }
                startActivityForResult(Intent.createChooser(target, "Image Chooser"), requestCode)
                return true
            }
    
        }
            
        //文件选择好后的回调activity回调
       override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            var isCallback = false
            //回调函数必须调用,不然下次选择会有问题
            filePathCallback?.also { callback ->
                if (data != null && resultCode == RESULT_OK) {
                    //这里是单选
                    data.data?.also {
                        callback.onReceiveValue(arrayOf(it))
                        isCallback = true
                    }
                    //这里是多选（仅图片）
                    data.clipData?.also {
                        val count = it.itemCount
                        val list = mutableListOf<Uri>()
                        for (index in 0 until count) {
                            list.add(it.getItemAt(index).uri)
                        }
                        callback.onReceiveValue((list.toTypedArray()))
                        isCallback = true
                    }
                }
            }
            if (!isCallback) {
                filePathCallback?.onReceiveValue(arrayOf())
            }
        }
```


###混淆后加载https问题

1.表现为启动页就是Webview页面就可以正常加载，经过一些跳转后加载WebView就无法加载（项目用了CC组件化功能）
2.暂时通过在启动页加载一下https页面


