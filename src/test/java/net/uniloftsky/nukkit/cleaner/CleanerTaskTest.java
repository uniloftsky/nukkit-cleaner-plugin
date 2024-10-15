package net.uniloftsky.nukkit.cleaner;

import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityItem;
import cn.nukkit.entity.item.EntityXPOrb;
import cn.nukkit.entity.passive.EntityWalkingAnimal;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.PluginLogger;
import net.uniloftsky.nukkit.cleaner.config.CleanerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class CleanerTaskTest {

    @Mock
    private CleanerPlugin plugin;

    @Mock
    private CleanerConfig config;

    private CleanerTask task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // mocking the clean index configuration
        when(config.isCleanItems()).thenReturn(true);
        when(config.isCleanXp()).thenReturn(false);
        when(config.isCleanAnimals()).thenReturn(true);

        task = new CleanerTask(plugin, config);
    }

    @Test
    public void testOnRun() {

        // given
        PluginLogger logger = mock(PluginLogger.class);
        given(plugin.getLogger()).willReturn(logger);

        Server server = mock(Server.class);
        given(plugin.getServer()).willReturn(server);

        Level world = mock(Level.class);
        given(server.getLevels()).willReturn(Map.of(1, world));

        // mocking level entities
        EntityItem mockedItemEntity = mock(EntityItem.class);
        EntityXPOrb mockedXpOrbEntity = mock(EntityXPOrb.class);
        EntityWalkingAnimal mockedWalkingAnimalEntity = mock(EntityWalkingAnimal.class);
        Entity[] worldEntities = new Entity[]{mockedItemEntity, mockedXpOrbEntity, mockedWalkingAnimalEntity};
        given(world.getEntities()).willReturn(worldEntities);

        // when
        task.onRun(0);

        // then
        then(world).should().unloadChunks();
        then(logger).should(times(3)).info(anyString());

        // should be cleared corresponding to mocked clean index
        then(mockedItemEntity).should().despawnFromAll();
        then(mockedWalkingAnimalEntity).should().despawnFromAll();

        // should not be cleared corresponding to mocked clean index
        then(mockedXpOrbEntity).should(times(0)).despawnFromAll();
    }

}
