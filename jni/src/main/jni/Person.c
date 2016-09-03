//
// Created by zovl on 2016/9/3.
//

#include <jni.h>

/*
 * Class:     zovl_zhongguanhua_jni_demo_jni_Person
 * Method:    getInfo
 * Signature: (Ljava/lang/String;IZ)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_zovl_zhongguanhua_jni_demo_jni_Person_getInfo
        (JNIEnv *env, jclass cls, jstring name, jint age, jboolean isMan) {

    jstring str = (*env)->NewStringUTF(env, "Hello JIN");
    return str;
}