#include <jni.h>
#include <string>
#include <vector>

const char part1[] = {86, 127, 115, 51, 33, 127, 61};
const char part2[] = {52, 44, 123, 59, 48, 111, 1};
const char part3[] = {97, 78, 10, 66, 7, 24, 90, 8};
const char part4[] = {32, 54, 90, 56, 34, 40, 43, 119, 50, 60};


std::string deobfuscate(const char* part, size_t size, int xorKey) {
    std::vector<char> deobfuscated_part(size);
    for (size_t i = 0; i < size; ++i) {
        deobfuscated_part[i] = part[i] ^ xorKey;
    }
    return std::string(deobfuscated_part.begin(), deobfuscated_part.end());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_lushan_securedapp_core_security_Keys_getSecretKey(
        JNIEnv* env,
        jobject) {

    // Reconstruct the key at runtime from its deobfuscated parts
    std::string p1 = deobfuscate(part1, sizeof(part1), 23); // Use key 23
    std::string p2 = deobfuscate(part2, sizeof(part2), 95); // Use key 88
    std::string p3 = deobfuscate(part3, sizeof(part3), 41); // Use key 88
    std::string p4 = deobfuscate(part4, sizeof(part4), 14); // Use key 88

    // Concatenate the parts in the correct order to get the final key
    std::string finalKey = p1 + p2 + p3 + p4;

    return env->NewStringUTF(finalKey.c_str());
}

