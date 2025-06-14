/*FreeMind - A Program for creating and viewing Mindmaps
 *Copyright (C) 2000-2004  Joerg Mueller, Daniel Polansky, Christian Foltin and others.
 *
 *See COPYING for Details
 *
 *This program is free software; you can redistribute it and/or
 *modify it under the terms of the GNU General Public License
 *as published by the Free Software Foundation; either version 2
 *of the License, or (at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program; if not, write to the Free Software
 *Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * Created on 25.08.2004
 */

package freemind.modes.mindmapmode.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import freemind.modes.mindmapmode.MindMapController;

public class NewPreviousSiblingAction extends AbstractAction {
	private final MindMapController controller;

	public NewPreviousSiblingAction(MindMapController controller) {
		super(controller.getText("new_sibling_before")); //original implementation
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("16. maroun" );
		controller.addNew(controller.getSelected(),				//maybe not needed if not wanting to call it through 
				MindMapController.NEW_SIBLING_BEFORE, null); //maybe not needed if not wanting to call it through 
	}
}