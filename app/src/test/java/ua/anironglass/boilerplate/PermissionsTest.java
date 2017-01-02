package ua.anironglass.boilerplate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.Fs;
import org.robolectric.res.FsFile;

import ua.anironglass.boilerplate.utils.DefaultConfig;

import static com.google.common.truth.Truth.assertThat;


@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = DefaultConfig.EMULATE_SDK)
public final class PermissionsTest {

    private static final String[] EXPECTED_RELEASE_PERMISSIONS = {
            "android.permission.INTERNET",
            "android.permission.ACCESS_NETWORK_STATE",
    };

    private static final String[] EXPECTED_DEBUG_PERMISSIONS = {
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
    public void shouldMatchPermissions() {
        // Initialize: configure test for current app configuration
        FsFile mergedManifestFile;
        Object[] expectedPermissions;
        if (BuildConfig.DEBUG) {
            mergedManifestFile = Fs.fileFromPath(MERGED_DEBUG_MANIFEST_FILE);
            expectedPermissions = EXPECTED_DEBUG_PERMISSIONS;
        } else {
            mergedManifestFile = Fs.fileFromPath(MERGED_RELEASE_MANIFEST_FILE);
            expectedPermissions = EXPECTED_RELEASE_PERMISSIONS;
        }

        // Run: Creates a Robolectric configuration using specified manifest file
        AndroidManifest manifest = new AndroidManifest(mergedManifestFile, null, null);

        // Check: manifest file should contain only expected permissions
        assertThat(manifest.getUsedPermissions())
                .containsExactly(expectedPermissions);
    }

}