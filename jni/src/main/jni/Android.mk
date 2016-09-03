LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE := TestNdk
LOCAL_SRC_FILES := com_ndk_test_JniClient.c
include $(BUILD_SHARED_LIBRARY)