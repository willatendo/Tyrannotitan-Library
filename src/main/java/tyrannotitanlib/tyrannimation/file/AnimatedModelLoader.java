package tyrannotitanlib.tyrannimation.file;

import static tyrannotitanlib.core.content.Util.LOG;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import tyrannotitanlib.tyrannimation.animation.exception.AnimationException;
import tyrannotitanlib.tyrannimation.animation.raw.pojo.Converter;
import tyrannotitanlib.tyrannimation.animation.raw.pojo.FormatVersion;
import tyrannotitanlib.tyrannimation.animation.raw.pojo.RawAnimatedModel;
import tyrannotitanlib.tyrannimation.animation.raw.tree.RawGeometryTree;
import tyrannotitanlib.tyrannimation.animation.render.AnimationBuilder;
import tyrannotitanlib.tyrannimation.animation.render.built.AnimationModel;

public class AnimatedModelLoader {
	public AnimationModel loadModel(ResourceManager resourceManager, ResourceLocation location) {
		try {
			RawAnimatedModel rawModel = Converter.fromJsonString(AnimatedFileLoader.getResourceAsString(location, resourceManager));
			if (rawModel.getFormatVersion() != FormatVersion.VERSION_1_12_0) {
				throw new AnimationException(location, "Wrong geometry json version, expected 1.12.0");
			}

			RawGeometryTree rawGeometryTree = RawGeometryTree.parseHierarchy(rawModel);

			return AnimationBuilder.getGeoBuilder(location.getNamespace()).constructGeoModel(rawGeometryTree);
		} catch (Exception e) {
			LOG.error(String.format("Error parsing %S", location), e);
			throw (new RuntimeException(e));
		}
	}
}
