package my.file.test.aoi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AoiEntity {

	private static final int MODE_WATCHER = 0;
	private static final int MODE_MARKER = 0;
	private static final float AOI_RADIS2 = 0;
	private static final int MODE_DROP = 0;
	private static final int MODE_MOVE = 0;
	private List<FObject> watcherStatic = new ArrayList<FObject>();
	private List<FObject> markerStatic = new ArrayList<FObject>();
	private List<FObject> watcherMove = new ArrayList<FObject>();
	private List<FObject> markerMove = new ArrayList<FObject>();
	private Map<Long, FObject> objMap = new HashMap<Long, FObject>();
	private FNode hot = new FNode();

	public static FObject createFObj(long id) {
		return new FObject(id);
	}

	public FObject mainPos(long id) {
		return objMap.get(id);
	}

	public void mapInsert(long id, FObject obj) {
		objMap.put(id, obj);
	}

	public FObject mapQuery(long id) {
		FObject obj = mainPos(id);
		if (obj == null) {
			obj = createFObj(id);
			objMap.put(id, obj);
		}
		return obj;
	}

	public void mapForeach(FHandler handler, Object obj) {
		for (FObject fObject : objMap.values()) {
			handler.handler(obj, fObject);
		}
	}

	public void mapDrop(long id) {
		objMap.remove(id);
	}

	public void mapDelete() {
		objMap = null;
	}

	public void mapNew() {
		objMap = new HashMap<Long, AoiEntity.FObject>();
	}

	public void grabFObject(FObject fObj) {
		++fObj.ref;
	}

	public void deleteFObject(FObject fObj) {
		fObj = null;
	}

	public void dropFObject(FObject fObj) {
		--fObj.ref;
		if (fObj.ref <= 0) {
			mapDrop(fObj.id);
			deleteFObject(fObj);
		}
	}

	public void release(AoiEntity entity) {
		entity = null;
	}

	public void release() {
		release(this);
	}

	public void copyPos(float des[], float src[]) {
		des[0] = src[0];
		des[1] = src[1];
		des[2] = src[2];
	}

	public boolean change_mode(FObject obj, boolean set_watcher,
			boolean set_marker) {
		boolean change = false;
		if (obj.mode == 0) {
			if (set_watcher) {
				obj.mode = MODE_WATCHER;
			}
			if (set_marker) {
				obj.mode |= MODE_MARKER;
			}
			return true;
		}
		if (set_watcher) {
			if (0 == (obj.mode & MODE_WATCHER)) {
				obj.mode |= MODE_WATCHER;
				change = true;
			}
		} else {
			if ((obj.mode & MODE_WATCHER) > 0) {
				obj.mode &= ~MODE_WATCHER;
				change = true;
			}
		}
		if (set_marker) {
			if (0 == (obj.mode & MODE_MARKER)) {
				obj.mode |= MODE_MARKER;
				change = true;
			}
		} else {
			if ((obj.mode & MODE_MARKER) > 0) {
				obj.mode &= ~MODE_MARKER;
				change = true;
			}
		}
		return change;
	}

	boolean is_near(float p1[], float p2[]) {
		return DIST2(p1, p2) < AOI_RADIS2 * 0.25f;
	}

	private float DIST2(float[] p1, float[] p2) {
		// TODO Auto-generated method stub
		return 0;
	}

	float dist2(FObject p1, FObject p2) {
		float d = DIST2(p1.pos, p2.pos);
		return d;
	}

	public void update(long id, char[] modeChars, float[] pos) {

		FObject obj = mapQuery(id);
		int i = 0;
		boolean set_watcher = false;
		boolean set_marker = false;

		for (i = 0; i < modeChars.length; ++i) {
			char m = modeChars[i];
			switch (m) {
			case 'w':
				set_watcher = true;
				break;
			case 'm':
				set_marker = true;
				break;
			case 'd':
				if (0 == (obj.mode & MODE_DROP)) {
					obj.mode = MODE_DROP;
					dropFObject(obj);
				}
				return;
			}
		}

		if ((obj.mode & MODE_DROP) > 0) {
			obj.mode &= ~MODE_DROP;
			grabFObject(obj);
		}

		boolean changed = change_mode(obj, set_watcher, set_marker);

		copyPos(obj.pos, pos);
		if (changed || !is_near(pos, obj.last)) {
			copyPos(obj.last, pos);
			obj.mode |= MODE_MOVE;
			++obj.version;
		}

	}

	private static class FObject {
		private int ref;
		private long id;
		private int version;
		private int mode;
		private float last[];
		private float pos[];

		public FObject(long id) {
			ref = 1;
			this.id = id;
			version = 0;
			mode = 0;
		}
	}

	private static class FNode {
		private FNode next;
		private FObject watcher;
		private FObject marker;
		private int wVersion;
		private int mVersion;
	}

	public interface FHandler {
		public void handler(Object obj, FObject fObj);
	}

}
