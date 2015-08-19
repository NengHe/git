(function (factory) {
  if (typeof define === 'function' && define.amd) {
    define(['jquery'], factory);
  } else if (typeof exports === 'object') {
    // Node / CommonJS
    factory(require('jquery'));
  } else {
    factory(jQuery);
  }
})(function ($) {

  'use strict';

  var console = window.console || { log: function () {} };

  function CropAvatar($element, originalUrl) {
    this.$container = $element;

    this.$avatarView = this.$container.find('.avatar-view');
    this.$avatar = this.$avatarView.find('img');
    this.$avatarModal = this.$container.find('#avatar-modal');
    this.$loading = this.$container.find('.loading');

    this.$avatarForm = this.$avatarModal.find('.avatar-form');
    this.$avatarUpload = this.$avatarForm.find('.avatar-upload');
    this.$avatarSrc = this.$avatarForm.find('.avatar-src');
    this.$avatarData = this.$avatarForm.find('.avatar-data');
    this.$avatarInput = this.$avatarForm.find('.avatar-input');
    this.$avatarSave = this.$avatarForm.find('.avatar-save');
    this.$avatarBtns = this.$avatarForm.find('.avatar-btns');

    this.$avatarWrapper = this.$avatarModal.find('.avatar-wrapper');
    this.$avatarPreview = this.$avatarModal.find('.avatar-preview');

    //加载iframe传过来的参数
    this.parentDialogId = extractParameterFromIframeSrc('parentDialogId');
    this.targetInputId = extractParameterFromIframeSrc('targetInputId');
    this.fixedCropBoxWidth = extractParameterFromIframeSrc('fixedCropBoxWidth');
    this.fixedCropBoxHeight = extractParameterFromIframeSrc('fixedCropBoxHeight');
    
    this.init();
    
    if(originalUrl != null){
    	this.url = originalUrl;
    	this.startCropper();
    }
    
  }

  CropAvatar.prototype = {
    constructor: CropAvatar,

    support: {
      fileList: !!$('<input type="file">').prop('files'),
      blobURLs: !!window.URL && URL.createObjectURL,
      formData: !!window.FormData
    },

    init: function () {
      this.support.datauri = this.support.fileList && this.support.blobURLs;

      if (!this.support.formData) {
        this.initIframe();
      }

      this.initTooltip();
      this.initModal();
      this.addListener();
    },

    addListener: function () {
      this.$avatarView.on('click', $.proxy(this.click, this));
      this.$avatarInput.on('change', $.proxy(this.change, this));
      this.$avatarForm.on('submit', $.proxy(this.submit, this));
    },

    initTooltip: function () {
      this.$avatarView.tooltip({
        placement: 'bottom'
      });
    },

    initModal: function () {
      this.$avatarModal.modal({
        show: true
      });
      
    },

    initPreview: function () {
      var url = this.$avatar.attr('src');

      this.$avatarPreview.html('<img src="' + url + '">');
    },

    initIframe: function () {
      var target = 'upload-iframe-' + (new Date()).getTime(),
          $iframe = $('<iframe>').attr({
            name: target,
            src: ''
          }),
          _this = this;

      // Ready ifrmae
      $iframe.one('load', function () {

        // respond response
        $iframe.on('load', function () {
          var data;

          try {
            data = $(this).contents().find('body').text();
          } catch (e) {
            console.log(e.message);
          }

          if (data) {
            try {
              data = $.parseJSON(data);
            } catch (e) {
              console.log(e.message);
            }

            _this.submitDone(data);
          } else {
            _this.submitFail('Image upload failed!');
          }

          _this.submitEnd();

        });
      });

      this.$iframe = $iframe;
      this.$avatarForm.attr('target', target).after($iframe.hide());
    },

    click: function () {
      this.$avatarModal.modal('show');
      this.initPreview();
    },

    change: function () {
      var files,
          file;

      if (this.support.datauri) {
        files = this.$avatarInput.prop('files');

        if (files.length > 0) {
          file = files[0];

          if (this.isImageFile(file)) {
            if (this.url) {
              URL.revokeObjectURL(this.url); // Revoke the old one
            }

            this.url = URL.createObjectURL(file);
            this.startCropper();
          }
        }
      } else {
        file = this.$avatarInput.val();

        if (this.isImageFile(file)) {
          this.syncUpload();
        }
      }
    },

    submit: function () {
    /** 考虑到剪裁后二次提交时，因为图片源为本地剪裁画布数据
      if (!this.$avatarSrc.val() && !this.$avatarInput.val()) {
        return false;
      }
      */

      if (this.support.formData) {
        this.ajaxUpload();
        return false;
      }
    },


    isImageFile: function (file) {
      if (file.type) {
        return /^image\/\w+$/.test(file.type);
      } else {
        return /\.(jpg|jpeg|png|gif)$/.test(file);
      }
    },

    startCropper: function () {
      var _this = this;

      if (this.active) {
        this.$img.cropper('replace', this.url);
      } else if(this.newUploaded) {	
    	//获取最新剪裁的canvas对应的本地字节流url
		var dataUrl = this.getCanvasToBlobUrl();
		this.$img.cropper('replace', dataUrl);
		
		this.active = true;
      } else {
        this.$img = $('<img src="' + this.url + '">');
        this.$avatarWrapper.empty().html(this.$img);
        
		this.$img.cropper({
		  strict: false,
		  guides: false,
		  autoCropArea: 0.5,
		  aspectRatio: this.fixedCropBoxWidth/this.fixedCropBoxHeight,
		  preview: this.$avatarPreview.selector,
		  dragCrop: false,
		  cropBoxMovable: false,
		  cropBoxResizable: false,
		  cropBoxFixed: true,
//		  fixedCropBoxWidth: this.fixedCropBoxWidth,
//		  fixedCropBoxHeight: this.fixedCropBoxHeight,
		  change : this.cropBoxChange
		});

        this.active = true;
      }
      
      this.$avatarModal.one('hidden.bs.modal', function () {
        _this.$avatarPreview.empty();
        _this.stopCropper();
      });
      
    },

    stopCropper: function () {
      if (this.active) {
//        this.$img.cropper('destroy');
//        this.$img.remove();
        this.active = false;
      }
    },

    ajaxUpload: function () {
      var url = this.$avatarForm.attr('action'),
//          data = new FormData(this.$avatarForm[0]),
      _this = this;
      
      var data= this.generateCropImageData();
      
      $.ajax(url, {
        type: 'post',
        data: data,
        dataType: 'json',
        processData: false,
        contentType: false,

        beforeSend: function () {
          _this.submitStart();
        },

        success: function (data) {
          _this.submitDone(data);
        },

        error: function (XMLHttpRequest, textStatus, errorThrown) {
          _this.submitFail(textStatus || errorThrown);
        },

        complete: function () {
          _this.submitEnd();
        }
      });
    },

    syncUpload: function () {
      this.$avatarSave.click();
    },

    submitStart: function () {
      this.$loading.fadeIn();
    },

    submitDone: function (data) {
//      console.log(data);

//      var result = eval("("+data+")");
      var result = data;
      if (result != null && result.code == '1') {
          this.url = result.result.colImgPath;

          if (this.support.datauri || this.uploaded) {
            this.uploaded = false;
            this.cropDone();
          } else {
            this.uploaded = true;
            this.$avatarSrc.val(this.url);
            this.startCropper();
          }
          
          //设置目标input标签对应图片url
          parent.document.getElementById(this.targetInputId).value = this.url;
          
          //重新显示图片为剪裁后的图片
//          this.newUploaded = true;
//          this.startCropper();
          
          //关闭当前页面
          this.closeCurCopperWin();
      } else {
        this.alert('Failed to response');
      }
    },

    submitFail: function (msg) {
      this.alert(msg);
    },

    submitEnd: function () {
      this.$loading.fadeOut();
    },

    cropDone: function () {
      this.$avatarForm.get(0).reset();
      this.$avatar.attr('src', this.url);
      this.stopCropper();
//      this.$avatarModal.modal('hide');
    },

    alert: function (msg) {
      var $alert = [
            '<div class="alert alert-danger avatar-alert alert-dismissable">',
              '<button type="button" class="close" data-dismiss="alert">&times;</button>',
              msg,
            '</div>'
          ].join('');

      this.$avatarUpload.after($alert);
    },
	
    /**
     * 生成剪裁图片数据，用于服务器上传
     * @returns {FormData}
     */
	generateCropImageData : function(){
		//根据前台设定值获取剪裁后的画布
		var canvas = this.getCertainCroppedCanvas();
//		var	context = canvas.getContext('2d');
		var dataUrl = canvas.toDataURL('image/png');
		//将dataUrl转换成字节流数据
		var blob = this.dataURLtoBlob(dataUrl);  

//		var url = window.URL.createObjectURL(blob);
//		document.getElementById("download").href=url;
//		var fireOnThis = document.getElementById('download');
//        var evObj = document.createEvent('MouseEvents');
//        evObj.initMouseEvent( 'click', true, true, window, 1, 12, 345, 7, 220, false, false, true, false, 0, null );
//        fireOnThis.dispatchEvent(evObj);
		
        var fd=new FormData();
        fd.append('imgFile', blob, 'image.png');
        
		return fd;
		
	},
    
	/**
	 * 将dataUrl转换成字节流数据
	 * @param dataurl
	 * @returns {Blob}
	 */
	dataURLtoBlob : function(dataurl) {
		var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
			bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
		while(n--){
			u8arr[n] = bstr.charCodeAt(n);
		}
		return new Blob([u8arr], {type:mime});
	},
	
	/**
	 * 根据当前canvas获取字节流对应的本地url
	 * @returns
	 */
	getCanvasToBlobUrl : function(){
		var blobUrl = null;
		//根据前台设定值获取剪裁后的画布
		var canvas = this.getCertainCroppedCanvas();
		var dataUrl = canvas.toDataURL('image/png');
		//将dataUrl转换成字节流数据
		var blob = this.dataURLtoBlob(dataUrl);  
		//创建字节流数据url
		blobUrl = window.URL.createObjectURL(blob);
		
		return blobUrl;
	},
	
	/**
	 * 根据前台设定值获取剪裁后的画布
	 * @param canvas
	 */
	getCertainCroppedCanvas : function(){
		var canvas=this.$img.cropper('getCroppedCanvas', {
			  cropBoxFixed: true,
			  fixedCropBoxWidth: this.fixedCropBoxWidth,
			  fixedCropBoxHeight: this.fixedCropBoxHeight
			});
		
		return canvas;
	},
	
	closeCurCopperWin : function(){
		parent.$('#cropper_img_win').dialog('close');
	}
	
  };

  $(function () {
	//初始化剪裁页面
    return new CropAvatar($('#crop-avatar'));
  });
  

  /**
   * 从iframe的src中抽取指定传参
   * @param parameterName
   * @returns
   */
  function extractParameterFromIframeSrc(parameterName){
	var iframeSrc = parent.document.getElementById('imgIframe').getAttribute('src');
  	var parameterValue = null;
  	var srcArr = null;
  	var paramsArr = null;
  	var valsArr = null;
  	
  	if(iframeSrc == null || iframeSrc.trim().length <= 0 
  			|| parameterName == null || parameterName.trim().length <= 0){
  		return null;
  	}
  	
  	srcArr = iframeSrc.split("?");
  	if(srcArr != null && srcArr.length > 1){
  		
  		//截取参数部分字符串
  		paramsArr = srcArr[1].split("&");	//多个参数
  		if(paramsArr != null && paramsArr.length > 1){
  			for(var i = 0; i < paramsArr.length; i ++){
  				//匹配要抽取的参数
  				if(paramsArr[i] == null && paramsArr[i].trim().length <=0){
  					continue;
  				}
  				
  				if(paramsArr[i].indexOf(parameterName+"=") > -1){	
  					valsArr = paramsArr[i].split("=");
  					if(valsArr != null && valsArr.length > 1){
  						parameterValue = valsArr[1];
  						
  						return parameterValue;
  					}
  				}
  			}
  		}else{	//单个参数
  			if(srcArr[1].indexOf(parameterName+"=") > -1){	
	  			valsArr = srcArr[1].split("=");
				if(valsArr != null && valsArr.length > 1){
					parameterValue = valsArr[1];
					
					return parameterValue;
				}
  			}
  		}

  	}
  	
  	return null;
  }
  
});
