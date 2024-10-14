package net.uniloftsky.nukkit.cleaner.config;

import cn.nukkit.plugin.PluginLogger;
import net.uniloftsky.nukkit.cleaner.CleanerPlugin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CleanerConfigTest {

    /**
     * Name of main config
     */
    private static final String MAIN_CONFIG = "config.json";

    /**
     * Mocked JSON configuration
     */
    private static final String MOCKED_JSON = "{\"interval\":1000,\"scope\":{\"items\":true,\"mobs\":true,\"animals\":false,\"xp\":true,\"projectiles\":false}}";

    private static final String MOCKED_JSON_WITHOUT_INTERVAL = "{\"scope\":{\"items\":true,\"mobs\":true,\"animals\":true,\"xp\":true,\"projectiles\":true}}";
    private static final String MOCKED_JSON_WITHOUT_SCOPE = "{\"interval\":1000}";

    /* These values should be defined respectively to MOCKED_JSON */
    private static final int INTERVAL = 1000;
    private static final boolean IS_CLEAN_ITEMS = true;
    private static final boolean IS_CLEAN_MOBS = true;
    private static final boolean IS_CLEAN_ANIMALS = false;
    private static final boolean IS_CLEAN_XP = true;
    private static final boolean IS_CLEAN_PROJECTILES = false;

    @Mock
    private CleanerPlugin plugin;

    @Mock
    private PluginLogger logger;

    @Spy
    @InjectMocks
    private CleanerConfig config;

    @Test
    public void testInit() throws IOException {

        // given
        mockLogger();

        doReturn(MOCKED_JSON).when(config).getConfigContents();

        // when
        boolean result = config.init();

        // then
        then(logger).should(times(3)).info(anyString());

        assertTrue(result);
        assertEquals(INTERVAL, config.getInterval());
        assertEquals(IS_CLEAN_ITEMS, config.isCleanItems());
        assertEquals(IS_CLEAN_MOBS, config.isCleanMobs());
        assertEquals(IS_CLEAN_ANIMALS, config.isCleanAnimals());
        assertEquals(IS_CLEAN_XP, config.isCleanXp());
        assertEquals(IS_CLEAN_PROJECTILES, config.isCleanProjectiles());
    }

    @Test
    public void testInitCannotGetConfig() throws IOException {

        // given
        mockLogger();

        doThrow(new IOException()).when(config).getConfigContents();

        // when
        boolean result = config.init();

        // then
        then(logger).should(times(2)).info(anyString());
        then(logger).should(times(1)).error(anyString());

        assertFalse(result);
    }

    @Test
    public void testInitCannotGetInterval() throws IOException {

        // given
        mockLogger();

        doReturn(MOCKED_JSON_WITHOUT_INTERVAL).when(config).getConfigContents();

        // when
        boolean result = config.init();

        // then
        then(logger).should(times(2)).info(anyString());
        then(logger).should(times(1)).error(anyString());

        assertFalse(result);
    }

    @Test
    public void testInitCannotGetScope() throws IOException {

        // given
        mockLogger();

        doReturn(MOCKED_JSON_WITHOUT_SCOPE).when(config).getConfigContents();

        // when
        boolean result = config.init();

        // then
        then(logger).should(times(2)).info(anyString());
        then(logger).should(times(1)).error(anyString());

        assertFalse(result);
    }

    @Test
    public void testGetConfigContents() throws IOException {

        // given
        File mockedPluginDataFolder = mock(File.class);
        given(plugin.getDataFolder()).willReturn(mockedPluginDataFolder);

        Path mockedPath = mock(Path.class);
        given(mockedPluginDataFolder.toPath()).willReturn(mockedPath);
        given(mockedPath.resolve(MAIN_CONFIG)).willReturn(mockedPath);

        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAllBytes(mockedPath)).thenReturn(MOCKED_JSON.getBytes()); // mocked configuration JSON file

            // when
            String result = config.getConfigContents();

            assertEquals(MOCKED_JSON, result);
        }
    }

    @Test
    public void testIsInitialized() {

        // when
        try {
            config.isInitialized();
        } catch (RuntimeException ignored) {
        }

    }

    /**
     * Invoke if logger should be mocked
     */
    private void mockLogger() {
        given(plugin.getLogger()).willReturn(logger);
    }
}
