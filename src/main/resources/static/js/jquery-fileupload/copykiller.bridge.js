/*
	Copyright (c) 2017, Muhayu Inc.
	All rights reserved.
*/
CopykillerBridge = {
	url: "",
	temp_config_id: new Date().getTime(),
	lang: "ko",

	msg: {
		seturl_error: {
			ko: "�붿껌�� �ㅽ뙣�덉뒿�덈떎. CopykillerBridge.setUrl瑜� 吏��뺥븯�� 二쇱떆湲� 諛붾엻�덈떎.",
			en: "The request failed. CopykillerBridge.setUrl is not specify."
		},
		request_error: {
			ko: "�붿껌�� �ㅽ뙣�덉뒿�덈떎. 吏��띿쟻�쇰줈 �ㅻ쪟 諛쒖깮�� 怨좉컼�쇳꽣濡� 臾몄쓽 諛붾엻�덈떎. T.1588-9784 E. help@copykiller.com",
			en: "The request failed. Please Contact CS Center. T.1588-9784 E. help@copykiller.com"
		},
		prepare_check: {
			ko: "�쒖젅寃��� 湲곕뒫�� 以�鍮꾩쨷�낅땲��.",
			en: "Plagiarism check function is being prepared."
		},
		popup_error: {
			ko: "�앹뾽 李⑤떒湲곕뒫 �뱀� �앹뾽李⑤떒 �꾨줈洹몃옩�� �숈옉以묒엯�덈떎. �앹뾽 李⑤떒 湲곕뒫�� �댁젣�� �� �ㅼ떆 �쒕룄�섏꽭��.",
			en: "Popup blocker is running. Disable pop-up blocking and try again."
		}
	},

	alert: function(message) {
		alert(message);
	},

	setUrl: function(url) {
		CopykillerBridge.url = url;
	},

	setMsg: function(msg) {
		CopykillerBridge.msg = msg;
	},

	setLang: function(lang) {
		CopykillerBridge.lang = lang;
	},

	getMessage: function(msg) {
		if (CopykillerBridge.msg[msg] != undefined) {
			if (CopykillerBridge.msg[msg][CopykillerBridge.lang] != undefined) {
				return CopykillerBridge.msg[msg][CopykillerBridge.lang];
			}

			if (CopykillerBridge.msg[msg]["ko"] != undefined) {
				return CopykillerBridge.msg[msg]["ko"];
			}

			return CopykillerBridge.msg[msg];
		}

		return msg;

	},

	popup: function(url, name, width, height, innerHTML) {
		var popup = window.open(url, name, "resizable=no, location=no, scrollbars=no, status=no, width=" + width + ", height=" + height);
		if (innerHTML != undefined && innerHTML != null) {
			popup.document.body.innerHTML = innerHTML;
		}
		return popup;
	},

	initConfigCopykillerGroup: function(selector, group_id, temp_config_id) {
		if (CopykillerBridge.url == "") {
			CopykillerBridge.alert(CopykillerBridge.getMessage('seturl_error'));
			return;
		}

		var request_temp_config_id = CopykillerBridge.temp_config_id;
		if (temp_config_id != undefined && temp_config_id) {
			request_temp_config_id = temp_config_id;
		}

		var config_btn = jQuery(selector);
		config_btn.unbind("click.config_copykiler_group");
		config_btn.on("click.config_copykiler_group", function() {
			var submitForm = null;
			jQuery.ajax({
				type: "post",
				async: false,
				url: CopykillerBridge.url,
				dataType: "json",
				data: {
					"act": "config_copykiller_group",
					"temp_config_id": request_temp_config_id,
					"group_id": group_id,
					"response_type": "json",
					"lang": CopykillerBridge.lang
				},
				success: function(data) {
					if (data.html != undefined) {
						//#5860 remove ckConfigPopup form if exists.
						var oldCkConfigPopup = document.getElementsByName("ckConfigPopup");
						if (oldCkConfigPopup.length > 0) {
							oldCkConfigPopup[0].parentNode.removeChild(oldCkConfigPopup[0]);
						}
						submitForm = jQuery('<form />', {
							action: CopykillerBridge.url,
							method: 'POST',
							name: 'ckConfigPopup',
							target: 'ckConfigPopup'
						});
						submitForm.appendTo('body');
						var inputAct = jQuery('<input />', {
							name: 'act',
							type: 'hidden',
							value: 'config_copykiller_group'
						});
						var inputTempConfigID = jQuery('<input />', {
							name: 'temp_config_id',
							type: 'hidden',
							value: request_temp_config_id
						});
						var inputGroupID = jQuery('<input />', { name: 'group_id', type: 'hidden', value: group_id });
						var inputLang = jQuery('<input />', {
							name: 'lang',
							type: 'hidden',
							value: CopykillerBridge.lang
						});
						submitForm.append(inputAct).append(inputTempConfigID).append(inputGroupID).append(inputLang);
					}
					else {
						CopykillerBridge.alert(CopykillerBridge.getMessage('request_error'));
					}

				},
				error: function() {
					CopykillerBridge.alert(CopykillerBridge.getMessage('request_error'));
				}
			});

			if (submitForm != null) {
				CopykillerBridge.popup("", "ckConfigPopup", 806, 570);
				submitForm.submit();
			}
		});
	},

	initStatusCopykillerGroup: function(selector, group_id) {
		if (CopykillerBridge.url == "") {
			CopykillerBridge.alert(CopykillerBridge.getMessage('seturl_error'));
			return;
		}

		var copy_check = jQuery(selector);
		var disable_message = CopykillerBridge.getMessage('prepare_check');
		var disable = true;
		var isfirst = true;

		copy_check.unbind("click.copy_check");
		copy_check.on("click.copy_check", function() {
			if (disable && !isfirst) {
				CopykillerBridge.alert(disable_message);
				return false;
			}
		});

		jQuery.ajax({
			type: "post",
			url: CopykillerBridge.url,
			data: {
				"act": "status_copykiller_group",
				"group_id": group_id,
				"lang": CopykillerBridge.lang
			},
			dataType: "json",
			success: function(data) {
				if (data.error == 0) {
					disable = data.disable;
					disable_message = data.disable_message;
					if (data.disable_modify) {
						if (data.value) {
							if (!copy_check.is(":checked")) {
								copy_check.trigger("click");
								//copy_check.attr("checked", true);
								isfirst = false;
							}							
						}
						else {
							if (copy_check.is(":checked")) {
								copy_check.trigger("click");
								//copy_check.attr("checked", false);
								isfirst = false;	
							}							
						}
					}
					else {
						disable = data.disable_modify;
					}
				}
			},
			error: function() {
				disable_message = CopykillerBridge.getMessage('request_error');
			}
		});

	},

	get_copykiller_info_sleep_second: 60,
	get_copykiller_info_max_try_count: 30,
	get_copykiller_info_try_count: 0,
	get_copykiller_info_notcompleted_uri_list: null,
	get_copykiller_info_set_time_out_handle: 0,


	initGetCopykillerInfo: function(selector, group_id) {
		if (CopykillerBridge.url == "") {
			CopykillerBridge.alert(CopykillerBridge.getMessage('seturl_error'));
			return;
		}

		if (CopykillerBridge.get_copykiller_info_set_time_out_handle != 0) {
			clearTimeout(CopykillerBridge.get_copykiller_info_set_time_out_handle);
			CopykillerBridge.get_copykiller_info_set_time_out_handle = 0;
		}
		CopykillerBridge.get_copykiller_info_try_count = 0;
		CopykillerBridge.get_copykiller_info_notcompleted_uri_list = null;

		if (group_id != undefined) {
			jQuery.ajax({
				type: "post",
				url: CopykillerBridge.url,
				data: {
					"act": "status_copykiller_group",
					"group_id": group_id,
					"lang": CopykillerBridge.lang
				},
				dataType: "json",
				success: function(data) {
					if (data.error == 0) {
						if (data.value) {
							set_time_out_handle = setTimeout('CopykillerBridge._initGetCopykillerInfo("' + selector + '", "' + group_id + '")', 0);
						}
					}
				}
			});
		}
	},

	_initGetCopykillerInfo: function(selector, group_id) {
		var uri_list = null;
		var notcompleted_uri_list = null;
		var update_date_list = null;
		var writer_id_list = null;
		var selectorObject = jQuery(selector);

		if (CopykillerBridge.get_copykiller_info_try_count > 0) {
			if (CopykillerBridge.get_copykiller_info_max_try_count <= CopykillerBridge.get_copykiller_info_try_count) {
				return;
			}
			else if (CopykillerBridge.get_copykiller_info_notcompleted_uri_list.length > 0) {
				notcompleted_uri_list = CopykillerBridge.get_copykiller_info_notcompleted_uri_list;
			}
			else {
				return;
			}
		}

		uri_list = [];
		update_date_list = [];
		writer_id_list = [];
		selectorObject.each(function() {
			var uri = jQuery(this).attr("uri");
			if (notcompleted_uri_list != null) {
				if (jQuery.inArray(uri, notcompleted_uri_list) == -1) return;
			}

			uri_list.push(uri);
			update_date_list.push(jQuery(this).attr("update_date"));
			writer_id_list.push(jQuery(this).attr("writer_id"));
			
			jQuery(this).attr("title", "GROUP_ID: " + group_id + " / URI: " + uri);
		});

		CopykillerBridge.get_copykiller_info_try_count += 1;
		CopykillerBridge.get_copykiller_info_notcompleted_uri_list = [];
		jQuery.ajax({
			type: "post",
			url: CopykillerBridge.url,
			data: {
				"act": "get_copykiller_info",
				"uri": uri_list.join(","),
				"group_id": group_id,
				"update_date": update_date_list.join(","),
				"writer_id": writer_id_list.join(","),
				"lang": CopykillerBridge.lang
			},
			dataType: "json",
			success: function(data) {
				if (data.error == 0) {
					var copykiller_info_list = jQuery.makeArray(data.copykiller_info);

					if (typeof (CopykillerBridge.showCopykillerInfo) == 'function') {
						CopykillerBridge.showCopykillerInfo(copykiller_info_list);
					}

					jQuery.map(copykiller_info_list, function(copykiller_info) {
						try {
							var viewObj = selectorObject.filter("[uri=" + copykiller_info.uri + "]");
							viewObj.text(copykiller_info.disp_total_copy_ratio);
							if (copykiller_info.complete_status == "Y") {
								if (copykiller_info.grant != undefined && copykiller_info.grant.view == 1) {
									viewObj.css('cursor', 'pointer');
									CopykillerBridge.bindCopykillerView(viewObj, copykiller_info.group_id, copykiller_info.uri);
								}
							} else if (copykiller_info.complete_status == "N") {
								CopykillerBridge.get_copykiller_info_notcompleted_uri_list.push(copykiller_info.uri);
							} else if (copykiller_info.complete_status == "F") {
								if (copykiller_info.disp_total_copy_ratio_detail_message) {
									viewObj.css('cursor', 'pointer');
									viewObj.attr('title', copykiller_info.disp_total_copy_ratio_detail_message);
								}
							}
						}
						catch (e) { };
					});
					setTimeout('CopykillerBridge._initGetCopykillerInfo("' + selector + '","' + group_id + '")', CopykillerBridge.get_copykiller_info_sleep_second * 1000);
				}
				else {
					jQuery(uri_list).each(function(index, item) {
						selectorObject.filter("[uri=" + item + "]").text("-");
					});
				}
			},
			error: function() {
				jQuery(uri_list).each(function(index, item) {
					selectorObject.filter("[uri=" + item + "]").text("-");
				});
			}
		});

	},

	getCopykillerInfo: function(group_id) {

		var result = [];
		jQuery.ajax({
			type: "post",
			url: CopykillerBridge.url,
			data: {
				"act": "get_copykiller_info",
				"group_id": group_id,
				"lang": CopykillerBridge.lang
			},
			async: false,
			dataType: "json",
			success: function(data) {
				if (data.error == 0) {
					result = jQuery.makeArray(data.copykiller_info);
				}
			},
			error: function() {

			}
		});
		return result;

	},

	escapeCopykillerViewUrl: function(url) {
		var redirectUrl = url;
		var idx = redirectUrl.lastIndexOf('http');
		if (idx > 0) {
			redirectUrl = redirectUrl.substr(0, idx) + encodeURIComponent(decodeURIComponent(redirectUrl.substr(idx, redirectUrl.length)))
		}
		return redirectUrl;
	},

	openCopykillerView: function(group_id, uri) {

		var openurl = null;
		jQuery.ajax({
			type: "post",
			async: false,
			url: CopykillerBridge.url,
			dataType: "json",
			data: {
				"act": "copykiller_view",
				"uri": uri,
				"group_id": group_id,
				"lang": CopykillerBridge.lang
			},
			success: function(data) {
				if (data.url != undefined) {
					openurl = CopykillerBridge.escapeCopykillerViewUrl(data.url);
				}
				else {
					CopykillerBridge.alert(CopykillerBridge.getMessage('request_error'));
				}
			},
			error: function() {
				CopykillerBridge.alert(CopykillerBridge.getMessage('request_error'));
			}
		});

		if (openurl != null) {
			var width = outerWidth - innerWidth;
			var height = outerHeight - innerHeight;
			CopykillerBridge.popup(openurl, "_blank", 1086 + width, 840 + height);
		}

	},

	bindCopykillerView: function(element, group_id, uri) {
		if (CopykillerBridge.url == "") {
			CopykillerBridge.alert(CopykillerBridge.getMessage('seturl_error'));
			return;
		}
		if(!element instanceof jQuery){
			element = jQuery(element);
		}

		element.unbind("click.copykiller_view");
		element.on("click.copykiller_view", function() {
			CopykillerBridge.openCopykillerView(group_id, uri);
		});
	},

	insertCopykillerUri: function(group_id, writer_id, uri) {
		if (CopykillerBridge.url == "") {
			CopykillerBridge.alert(CopykillerBridge.getMessage('seturl_error'));
			return;
		}

		jQuery.ajax({
			type: "post",
			url: CopykillerBridge.url,
			async: false,
			data: {
				"act": "insert_copykiller_uri",
				"group_id": group_id,
				"writer_id": writer_id,
				"uri": uri
			},
			success: function() {
				;
			},
			error: function() {
				;
			}
		});
	}
}