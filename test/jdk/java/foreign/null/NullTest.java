/*
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

import java.foreign.Libraries;
import java.foreign.NativeTypes;
import java.foreign.annotations.NativeHeader;
import java.foreign.memory.Pointer;

import static java.lang.invoke.MethodHandles.lookup;

import static org.testng.Assert.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @test
 * @run testng NullTest
 */

@Test
public class NullTest {

    @NativeHeader(declarations=
        "get_nullptr=()u64:i32"
    )
    interface LibNull {
        Pointer<Integer> get_nullptr();
    }

    private static final LibNull lib = Libraries.bind(LibNull.class, Libraries.loadLibrary(lookup(), "Null"));

    @Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = ".*Cannot dereference null.*")
    public void testDerefNull() {
        Pointer<Integer> intP = lib.get_nullptr();
        intP.get(); // NPE
    }
}