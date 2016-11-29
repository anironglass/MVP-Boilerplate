package ua.anironglass.template;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.Fs;

import ua.anironglass.template.utils.DefaultConfig;

import static com.google.common.truth.Truth.assertThat;


@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = DefaultConfig.EMULATE_SDK)
public final class PermissionsTest {

    private static final Object[] EXPECTED_RELEASE_PERMISSIONS = {
            "android.permission.INTERNET",
            "android.permission.ACCESS_NETWORK_STATE",
    };

    private static final Object[] EXPECTED_DEBUG_PERMISSIONS = {
            "android.permission.INTERNET",
            "android.permission.ACCESS_NETWORK_STATE",
            "android.permission.DISABLE_KEYGUARD",
            "android.permission.WAKE_LOCK",
            "android.permission.READ_LOGS",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
    };

    private static final String MERGED_RELEASE_MANIFEST_FILE =
            "build/intermediates/manifests/full/release/AndroidManifest.xml";

    private static final String MERGED_DEBUG_MANIFEST_FILE =
            "build/intermediates/manifests/full/debug/AndroidManifest.xml";


    @Test
    public void shouldMatchReleasePermissions() {
        AndroidManifest manifest = new AndroidManifest(
                Fs.fileFromPath(MERGED_RELEASE_MANIFEST_FILE),
                null,
                null
        );
        assertThat(manifest.getUsedPermissions())
                .containsExactly(EXPECTED_RELEASE_PERMISSIONS);
    }

    @Test
    public void shouldMatchDebugPermissions() {
        AndroidManifest manifest = new AndroidManifest(
                Fs.fileFromPath(MERGED_DEBUG_MANIFEST_FILE),
                null,
                null
        );
        assertThat(manifest.getUsedPermissions())
                .containsExactly(EXPECTED_DEBUG_PERMISSIONS);
    }

}