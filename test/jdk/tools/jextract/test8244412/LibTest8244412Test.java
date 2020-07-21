/*
 * Copyright (c) 2020, Oracle and/or its affiliates. All rights reserved.
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


import jdk.incubator.foreign.MemoryAccess;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.NativeScope;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static test.jextract.test8244412.test8244412_h.*;

/*
 * @test
 * @library ..
 * @modules jdk.incubator.jextract
 * @bug 8244412
 * @summary jextract should generate static utils class for primitive typedefs
 * @run driver JtregJextract -t test.jextract.test8244412 -- test8244412.h
 * @run testng/othervm -Dforeign.restricted=permit LibTest8244412Test
 */
public class LibTest8244412Test {
    @Test
    public void test() {
        try (var scope = NativeScope.unboundedScope()) {
            var addr = scope.allocate(mysize_t, 0L);
            assertEquals(MemoryAccess.getLong(addr), 0L);
            MemoryAccess.setLong(addr, 13455566L);
            assertEquals(MemoryAccess.getLong(addr), 13455566L);
        }
    }
}
