# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


# Quantity of optimisation iterations, correct number can be picked by enumeration method
#-optimizationpasses 5

#-dontobfuscate

# Minification
# Can remove calls to methods, if it can determine that the return values aren't used. Works with java code
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    public static void checkNotNullParameter(...);
    public static void checkParameterIsNotNull(...);
    public static void checkFieldIsNotNull(...);
    public static void checkFieldIsNotNull(...);
    public static void checkReturnedValueIsNotNull(...);
    public static void checkExpressionValueIsNotNull(...);
    public static void checkNotNullExpressionValue(...);
    public static void throwUndefinedForReified(...);
}

-keep public class dev.aggregate.network.model.*
-keep public class kotlin.Result { *; }
