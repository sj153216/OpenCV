#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_sj_myapplication_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}




extern "C"
JNIEXPORT jint JNICALL
Java_com_example_sj_myapplication_MainActivity_intFromJNI(JNIEnv *env, jobject instance) {

    // TODO

    int i = 23;
    return i;

}


extern "C"
JNIEXPORT jboolean JNICALL
Java_com_example_sj_myapplication_MainActivity_ijj(JNIEnv *env, jobject instance) {

    return true;

    // TODO

}