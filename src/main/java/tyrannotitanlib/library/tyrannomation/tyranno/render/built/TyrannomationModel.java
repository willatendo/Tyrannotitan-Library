package tyrannotitanlib.library.tyrannomation.tyranno.render.built;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import tyrannotitanlib.library.tyrannomation.tyranno.raw.pojo.ModelProperties;

public class TyrannomationModel 
{
	public List<TyrannomationBone> topLevelBones = new ArrayList<>();
	public ModelProperties properties;

	public Optional<TyrannomationBone> getBone(String name) 
	{
		for(TyrannomationBone bone : topLevelBones) 
		{
			TyrannomationBone optionalBone = getBoneRecursively(name, bone);
			if(optionalBone != null) 
			{
				return Optional.of(optionalBone);
			}
		}
		return Optional.empty();
	}

	private TyrannomationBone getBoneRecursively(String name, TyrannomationBone bone) 
	{
		if(bone.name.equals(name)) 
		{
			return bone;
		}
		for(TyrannomationBone childBone : bone.childBones) 
		{
			if(childBone.name.equals(name)) 
			{
				return childBone;
			}
			TyrannomationBone optionalBone = getBoneRecursively(name, childBone);
			if(optionalBone != null) 
			{
				return optionalBone;
			}
		}
		return null;
	}
}
