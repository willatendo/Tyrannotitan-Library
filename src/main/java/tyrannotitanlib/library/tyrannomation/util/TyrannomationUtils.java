package tyrannotitanlib.library.tyrannomation.util;

import net.minecraft.client.renderer.model.ModelRenderer;
import tyrannotitanlib.library.tyrannomation.core.processor.IBone;

public class TyrannomationUtils 
{
	public static void copyRotations(ModelRenderer from, IBone to) 
	{
		to.setRotationX(-from.xRot);
		to.setRotationY(-from.yRot);
		to.setRotationZ(from.zRot);
	}
}
