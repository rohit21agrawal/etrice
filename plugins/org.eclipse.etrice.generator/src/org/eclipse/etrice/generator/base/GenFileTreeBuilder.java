/*******************************************************************************
 * Copyright (c) 2011 protos software gmbh (http://www.protos.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * CONTRIBUTORS:
 * 		Thomas Schuetz and Henrik Rentz-Reichert (initial contribution)
 * 
 *******************************************************************************/

package org.eclipse.etrice.generator.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;

/**
 * @author Henrik Rentz-Reichert
 *
 */
public class GenFileTreeBuilder {
	
	private GenDir genFileTree;
	private IFileFilter filter; 
	
	public GenFileTreeBuilder(String uri, Collection<String> files, IFileFilter filter) {
		this(getURI(uri), files, filter);
	}
	
	public GenFileTreeBuilder(URI base, Collection<String> files, IFileFilter filter) {
		this.filter = filter;
		ArrayList<String> relPaths = computeFilesAsRelativePaths(base, files);
		genFileTree = computeGenTree(relPaths);
	}
	
	/**
	 * @return the genTree
	 */
	public GenDir getGenFileTree() {
		return genFileTree;
	}
	
	private ArrayList<String> computeFilesAsRelativePaths(URI base, Collection<String> files) {
		ArrayList<String> relFiles = new ArrayList<String>(files.size());
		for (String file : files) {
			String relPath = FileSystemHelpers.getRelativePath(base, URI.createFileURI(file.replace("\\\\", "\\")));
			if (relPath!=null && (filter==null || filter.accept(relPath)))
				relFiles.add(relPath);
		}
		Collections.sort(relFiles);
		return relFiles;
	}

	private GenDir computeGenTree(ArrayList<String> relPaths) {
		GenDir root = new GenDir(null, "root");
		for (String path : relPaths) {
			String[] segments = path.split("/");
			GenDir dir = makeDir(root, segments);
			new GenFile(dir, segments[segments.length-1]);
		}
		return root;
	}
	
	private GenDir makeDir(GenDir current, String[] segments) {
		for (int i = 0; i < segments.length-1; i++) {
			GenDir next = null;
			for (GenItem item : current.getContents()) {
				if (item instanceof GenDir && item.getName().equals(segments[i])) {
					next = (GenDir) item;
					break;
				}
			}
			if (next==null) {
				next = new GenDir(current, segments[i]);
			}
			current = next;
		}
		return current;
	}

	private static URI getURI(String uri) {
		URI base = URI.createFileURI(uri);
		if (base.hasTrailingPathSeparator())
			base = base.trimSegments(1);
		return base;
	}

}
