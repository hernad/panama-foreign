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

import java.nio.file.Path;

import jdk.incubator.foreign.CSupport;
import jdk.incubator.foreign.GroupLayout;
import jdk.incubator.foreign.MemoryLayout;
import org.testng.annotations.Test;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/*
 * @test
 * @modules jdk.incubator.jextract
 * @library /test/lib
 * @build JextractToolRunner
 * @bug 8240811
 * @summary jextract generates non-compilable code for name collision between a struct and a global variable
 * @run testng/othervm -Dforeign.restricted=permit Test8240811
 */
public class Test8240811 extends JextractToolRunner {
    @Test
    public void testNameCollision() {
        Path nameCollisionOutput = getOutputFilePath("name_collision_gen");
        Path nameCollisionH = getInputFilePath("name_collision.h");
        run("-d", nameCollisionOutput.toString(), nameCollisionH.toString()).checkSuccess();
        try(Loader loader = classLoader(nameCollisionOutput)) {
            Class<?> cls = loader.loadClass("name_collision_h");
            assertNotNull(cls);

            // check foo layout
            Class<?> fooCls = loader.loadClass("name_collision_h$foo");
            MemoryLayout fooLayout = findLayout(fooCls);
            assertNotNull(fooLayout);
            assertTrue(((GroupLayout)fooLayout).isStruct());
            checkField(fooLayout, "x",  CSupport.C_INT);
            checkField(fooLayout, "y",  CSupport.C_INT);
            checkField(fooLayout, "z",  CSupport.C_INT);

            MemoryLayout fooVarLayout = findLayout(cls, "foo");
            assertNotNull(fooVarLayout);

            // check foo2 layout
            Class<?> foo2Cls = loader.loadClass("name_collision_h$foo2");
            MemoryLayout foo2Layout = findLayout(foo2Cls);
            assertNotNull(foo2Layout);
            assertTrue(((GroupLayout)foo2Layout).isUnion());
            checkField(foo2Layout, "i",  CSupport.C_INT);
            checkField(foo2Layout, "l",  CSupport.C_LONG);

            MemoryLayout foo2VarLayout = findLayout(cls, "foo2");
            assertNotNull(foo2VarLayout);

            MemoryLayout barVarLayout = findLayout(cls, "bar");
            assertNotNull(barVarLayout);

            // check bar layout
            Class<?> barCls = loader.loadClass("name_collision_h$bar");
            MemoryLayout barLayout = findLayout(barCls);
            assertNotNull(barLayout);
            assertTrue(((GroupLayout)barLayout).isStruct());
            checkField(barLayout, "f1",  CSupport.C_FLOAT);
            checkField(barLayout, "f2",  CSupport.C_FLOAT);

            MemoryLayout bar2VarLayout = findLayout(cls, "bar2");
            assertNotNull(bar2VarLayout);

            // check bar layout
            Class<?> bar2Cls = loader.loadClass("name_collision_h$bar2");
            MemoryLayout bar2Layout = findLayout(bar2Cls);
            assertNotNull(bar2Layout);
            assertTrue(((GroupLayout)bar2Layout).isUnion());
            checkField(bar2Layout, "f",  CSupport.C_FLOAT);
            checkField(bar2Layout, "d",  CSupport.C_DOUBLE);
        } finally {
            deleteDir(nameCollisionOutput);
        }
    }
}
