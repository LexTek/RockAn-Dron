ifeq ("$(TARGET_OS_FLAVOUR)","native")

LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_CATEGORY_PATH := customsample
LOCAL_MODULE := CustomSample
LOCAL_DESCRIPTION := custom sample

LOCAL_LIBRARIES := \
	libARSAL \
	libARController \
	libARDataTransfer \
	libARUtils \
	libARCommands \
	libARNetwork \
	libARNetworkAL \
	libARDiscovery \
	libARStream \
	libARStream2 \
	ncurses

LOCAL_SRC_FILES := \
	$(call all-c-files-under,.)

include $(BUILD_EXECUTABLE)

endif
