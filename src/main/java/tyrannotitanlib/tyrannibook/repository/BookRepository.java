package tyrannotitanlib.tyrannibook.repository;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import tyrannotitanlib.tyrannibook.data.SectionData;

public abstract class BookRepository {
	public static final BookRepository DUMMY = new DummyRepository();

	public abstract List<SectionData> getSections();

	@Nullable
	public ResourceLocation getResourceLocation(@Nullable String path) {
		return this.getResourceLocation(path, false);
	}

	@Nullable
	public abstract ResourceLocation getResourceLocation(@Nullable String path, boolean safe);

	@Nullable
	public abstract Resource getResource(@Nullable ResourceLocation loc);

	public boolean resourceExists(@Nullable String location) {
		if (location == null) {
			return false;
		}

		return this.resourceExists(new ResourceLocation(location));
	}

	public abstract boolean resourceExists(@Nullable ResourceLocation location);

	public String resourceToString(@Nullable Resource resource) {
		return this.resourceToString(resource, true);
	}

	public abstract String resourceToString(@Nullable Resource resource, boolean skipComments);
}
