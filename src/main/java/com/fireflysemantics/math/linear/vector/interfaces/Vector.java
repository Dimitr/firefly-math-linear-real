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

package com.fireflysemantics.math.linear.vector.interfaces;

import com.fireflysemantics.math.exception.MathException;

/**
 * Interface defining basic vector operations.
 */
public interface Vector {

	/**
	 * Returns the size of the vector.
	 *
	 * @return the size of this vector.
	 */
	int getDimension();

	/**
	 * Return the element at the specified index.
	 *
	 * @param index
	 *            Index location of the element.
	 * @return the element at {@code index}.
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if the index is not valid.
	 * @see #setEntry(long, double)
	 */
	double getEntry(int index);

	/**
	 * Set a single element.
	 *
	 * @param index
	 *            element index.
	 * @param value
	 *            element value.
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if the index is not valid.
	 * @see #getEntry(int)
	 */
	void setEntry(int index, double value);
}
