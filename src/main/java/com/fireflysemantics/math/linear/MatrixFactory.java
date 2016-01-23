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

package com.fireflysemantics.math.linear;

import com.fireflysemantics.math.linear.matrix.SimpleMatrix;
import com.fireflysemantics.math.linear.matrix.interfaces.Matrix;

/** Factory for matrices */
public class MatrixFactory {
	/**
	 * Returns a {@link RealMatrix} with specified dimensions.
	 * <p>
	 * The type of matrix returned depends on the dimension. Below 2
	 * <sup>12</sup> elements (i.e. 4096 elements or 64&times;64 for a square
	 * matrix) which can be stored in a 32kB array, a
	 * {@link Array2DRowRealMatrix} instance is built. Above this threshold a
	 * {@link BlockRealMatrix} instance is built.
	 * </p>
	 * <p>
	 * The matrix elements are all set to 0.0.
	 * </p>
	 * 
	 * @param rows
	 *            number of rows of the matrix
	 * @param columns
	 *            number of columns of the matrix
	 * @return RealMatrix with specified dimensions
	 * @see #createRealMatrix(double[][])
	 */
	public static Matrix create(final int rows, final int columns) {
		return new SimpleMatrix(rows, columns);
		// TODO Fix if BlockMatrix support is implemented
		// return (rows * columns <= 4096) ? new SimpleMatrix(rows, columns) :
		// new BlockMatrix(rows, columns);
	}

}
