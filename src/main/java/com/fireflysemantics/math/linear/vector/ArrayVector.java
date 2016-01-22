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

package com.fireflysemantics.math.linear.vector;

import static com.fireflysemantics.math.exception.ExceptionFactory.checkIndexOutOfRange;

import com.fireflysemantics.math.linear.vector.interfaces.Vector;

import lombok.Getter;

/**
 * Implements {@link interfaces.Vector} backed by double[].
 */
public class ArrayVector implements Vector {

	/**
	 * @return the entries of the vector
	 */
	@Getter
	private double[] data;

	/**
	 * Construct a vector from an array.
	 * 
	 * If the vectors data should be independent of the underlying the
	 * {@code sourceArray} argument, then clone the array.
	 * 
	 * Example: {@code new ArrayVector(sourceArray.clone());}
	 *
	 * @param sourceArray
	 *            Array.
	 */
	public ArrayVector(double[] sourceArray) {
		this.data = sourceArray;
	}

	/** {@inheritDoc} */
	public int getDimension() {
		return data.length;
	}

	/** {@inheritDoc} */
	public double getEntry(int index) {
		checkIndexOutOfRange(index, 0, getDimension());
		return data[index];
	}

	/** {@inheritDoc} */
	public void setEntry(int index, double value) {
		checkIndexOutOfRange(index, 0, getDimension());
		data[index] = value;
	}
}
