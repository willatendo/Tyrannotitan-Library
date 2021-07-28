package tyrannotitanlib.library.tyrannomation.tyranno.raw.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import tyrannotitanlib.library.tyrannomation.tyranno.raw.pojo.Bone;
import tyrannotitanlib.library.tyrannomation.tyranno.raw.pojo.MinecraftGeometry;
import tyrannotitanlib.library.tyrannomation.tyranno.raw.pojo.ModelProperties;
import tyrannotitanlib.library.tyrannomation.tyranno.raw.pojo.RawTyrannomationModel;

public class RawGeometryTree 
{
	public HashMap<String, RawBoneGroup> topLevelBones = new HashMap<>();
	public ModelProperties properties;

	public static RawGeometryTree parseHierarchy(RawTyrannomationModel model) 
	{
		RawGeometryTree hierarchy = new RawGeometryTree();
		MinecraftGeometry geometry = model.getMinecraftGeometry()[0];
		hierarchy.properties = geometry.getProperties();
		List<Bone> bones = new ArrayList<>(Arrays.asList(geometry.getBones()));

		int index = bones.size() - 1;
		while(true) 
		{
			Bone bone = bones.get(index);
			if(!hasParent(bone)) 
			{
				hierarchy.topLevelBones.put(bone.getName(), new RawBoneGroup(bone));
				bones.remove(bone);
			} 
			else 
			{
				RawBoneGroup groupFromHierarchy = getGroupFromHierarchy(hierarchy, bone.getParent());
				if(groupFromHierarchy != null) 
				{
					groupFromHierarchy.children.put(bone.getName(), new RawBoneGroup(bone));
					bones.remove(bone);
				}
			}

			if(index == 0) 
			{
				index = bones.size() - 1;
				if(index == -1) 
				{
					break;
				}
			} 
			else 
			{
				index--;
			}
		}
		return hierarchy;
	}

	public static boolean hasParent(Bone bone) 
	{
		return bone.getParent() != null;
	}

	public static RawBoneGroup getGroupFromHierarchy(RawGeometryTree hierarchy, String bone) 
	{
		HashMap<String, RawBoneGroup> flatList = new HashMap<>();
		for(RawBoneGroup group : hierarchy.topLevelBones.values()) 
		{
			flatList.put(group.selfBone.getName(), group);
			traverse(flatList, group);
		}
		return flatList.get(bone);
	}

	public static void traverse(HashMap<String, RawBoneGroup> flatList, RawBoneGroup group) 
	{
		for(RawBoneGroup child : group.children.values()) 
		{
			flatList.put(child.selfBone.getName(), child);
			traverse(flatList, child);
		}
	}
}
