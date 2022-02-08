package tyrannimation.file;

import static core.content.Util.LOG;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import tyrannimation.animation.exception.TyrannotitanLibException;
import tyrannimation.animation.raw.pojo.Converter;
import tyrannimation.animation.raw.pojo.FormatVersion;
import tyrannimation.animation.raw.pojo.RawTyrannomationModel;
import tyrannimation.animation.raw.tree.RawGeometryTree;
import tyrannimation.animation.render.TyrannomationBuilder;
import tyrannimation.animation.render.built.TyrannomationModel;

public class AnimatedModelLoader {
	public TyrannomationModel loadModel(ResourceManager resourceManager, ResourceLocation location) {
		try {
			RawTyrannomationModel rawModel = Converter.fromJsonString(AnimatedFileLoader.getResourceAsString(location, resourceManager));
			if (rawModel.getFormatVersion() != FormatVersion.VERSION_1_12_0) {
				throw new TyrannotitanLibException(location, "Wrong geometry json version, expected 1.12.0");
			}

			RawGeometryTree rawGeometryTree = RawGeometryTree.parseHierarchy(rawModel);

			return TyrannomationBuilder.getGeoBuilder(location.getNamespace()).constructGeoModel(rawGeometryTree);
		} catch (Exception e) {
			LOG.error(String.format("Error parsing %S", location), e);
			throw (new RuntimeException(e));
		}
	}
}
