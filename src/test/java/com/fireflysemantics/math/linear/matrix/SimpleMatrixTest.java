/**
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.fireflysemantics.math.linear.matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class SimpleMatrixTest {

	@Test
	public void constructor() {
		double[][] data = { { 1, 2 }, { 3, 4 } };

		SimpleMatrix matrix = new SimpleMatrix(data);

		assertSame(data, matrix.toArray());
		assertSame(data[0], matrix.toArray()[0]);
		assertEquals(data[0][0], matrix.toArray()[0][0], Double.MIN_VALUE);
	}
}
