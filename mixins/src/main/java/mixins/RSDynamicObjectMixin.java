/*
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package mixins;

import dev.hoot.api.widgets.DialogOption;
import eventbus.Events;
import eventbus.events.DialogProcessed;
import eventbus.events.DynamicObjectAnimationChanged;
import net.runelite.api.mixins.Copy;
import net.runelite.api.mixins.FieldHook;
import net.runelite.api.mixins.Inject;
import net.runelite.api.mixins.MethodHook;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Replace;
import net.runelite.api.mixins.Shadow;
import net.runelite.rs.api.RSClient;
import net.runelite.rs.api.RSDynamicObject;
import net.runelite.rs.api.RSRenderable;
import net.runelite.rs.api.RSModel;

@Mixin(RSDynamicObject.class)
public abstract class RSDynamicObjectMixin implements RSDynamicObject
{
	@Shadow("client")
	private static RSClient client;

	@SuppressWarnings("InfiniteRecursion")
	@Copy("getModel")
	@Replace("getModel")
	public RSModel copy$getModel()
	{
		try
		{
			// reset frame because it may have been set from the constructor
			// it should be set again inside the getModel method
			int animFrame = getAnimFrame();
			if (animFrame < 0)
			{
				setAnimFrame((animFrame ^ Integer.MIN_VALUE) & 0xFFFF);
			}
			return copy$getModel();
		}
		finally
		{
			int animFrame = getAnimFrame();
			if (animFrame < 0)
			{
				setAnimFrame((animFrame ^ Integer.MIN_VALUE) & 0xFFFF);
			}
		}
	}

	@FieldHook("cycleStart")
	@Inject
	public void onAnimCycleCountChanged(int idx)
	{
		if (client.isInterpolateObjectAnimations())
		{
			// sets the packed anim frame with the frame cycle
			int objectFrameCycle = client.getGameCycle() - getAnimCycleCount();
			setAnimFrame(Integer.MIN_VALUE | objectFrameCycle << 16 | getAnimFrame());
		}
	}

	@MethodHook(value = "<init>", end = true)
	@Inject
	public void rl$init(int id, int type, int orientation, int plane, int x, int y, int animationID, boolean var8, RSRenderable var9)
	{
		if (animationID != -1)
		{
			DynamicObjectAnimationChanged dynamicObjectAnimationChanged = new DynamicObjectAnimationChanged(id, animationID, x, y);
			client.getCallbacks().post(Events.DYNAMIC_OBJECT_ANIMATION_CHANGED, dynamicObjectAnimationChanged);
		}
	}

	@Inject
	@Override
	public int getAnimationID()
	{
		return (int) (getAnimation() == null ? -1 : getAnimation().getHash());
	}

	@Inject
	@MethodHook("resumePauseWidget")
	public static void onDialogProcessed(int widgetUid, int menuIndex)
	{
		DialogOption dialogOption = DialogOption.of(widgetUid, menuIndex);
		if (dialogOption != null)
		{
			client.getCallbacks().post(Events.DIALOG_PROCESSED, new DialogProcessed(dialogOption));
		}
		else
		{
			client.getLogger().debug(
					"Unknown or unmapped dialog option for widgetUid: {} and menuIndex {}",
					widgetUid,
					menuIndex
			);
		}
	}
}
