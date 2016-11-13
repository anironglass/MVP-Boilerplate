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

    private static final Object[] EXPECTED_PERMISSIONS = {
            "android.permission.INTERNET",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
    };

    private static final String MERGED_MANIFEST_FILE =
            "build/intermediates/manifests/full/debug/AndroidManifest.xml";

    @Test
    public void shouldMatchPermissions() {
        AndroidManifest manifest = new AndroidManifest(
                Fs.fileFromPath(MERGED_MANIFEST_FILE),
                null,
                null
        );

        assertThat(manifest.getUsedPermissions())
                .containsExactly(EXPECTED_PERMISSIONS);
    }

}