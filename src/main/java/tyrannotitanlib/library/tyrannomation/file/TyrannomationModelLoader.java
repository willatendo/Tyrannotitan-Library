package tyrannotitanlib.library.tyrannomation.file;

import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import tyrannotitanlib.library.base.util.TyrannoUtils;
import tyrannotitanlib.library.tyrannomation.tyranno.exception.TyrannotitanLibException;
import tyrannotitanlib.library.tyrannomation.tyranno.raw.pojo.Converter;
import tyrannotitanlib.library.tyrannomation.tyranno.raw.pojo.FormatVersion;
import tyrannotitanlib.library.tyrannomation.tyranno.raw.pojo.RawTyrannomationModel;
import tyrannotitanlib.library.tyrannomation.tyranno.raw.tree.RawGeometryTree;
import tyrannotitanlib.library.tyrannomation.tyranno.render.TyrannomationBuilder;
import tyrannotitanlib.library.tyrannomation.tyranno.render.built.TyrannomationModel;

public class TyrannomationModelLoader 
{
	public TyrannomationModel loadModel(IResourceManager resourceManager, ResourceLocation location) 
	{
		try 
		{
			RawTyrannomationModel rawModel = Converter.fromJsonString(AnimationFileLoader.getResourceAsString(location, resourceManager));
			if(rawModel.getFormatVersion() != FormatVersion.VERSION_1_12_0) 
			{
				throw new TyrannotitanLibException(location, "Wrong geometry json version, expected 1.12.0");
			}

			RawGeometryTree rawGeometryTree = RawGeometryTree.parseHierarchy(rawModel);

			return TyrannomationBuilder.getGeoBuilder(location.getNamespace()).constructGeoModel(rawGeometryTree);
		} 
		catch(Exception e) 
		{
			TyrannoUtils.LOGGER.error(String.format("Error parsing %S", location), e);
			throw (new RuntimeException(e));
		}
	}
}
