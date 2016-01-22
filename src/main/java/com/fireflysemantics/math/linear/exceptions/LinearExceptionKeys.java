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
package com.fireflysemantics.math.linear.exceptions;

import com.fireflysemantics.math.exception.interfaces.ExceptionKey;

public enum LinearExceptionKeys implements ExceptionKey {
	START,
	END,
	SQUARE_DIMENSION,
	ROW_DIMENSION,
	COLUMN_DIMENSION,
	ROW_INDEX,
	COLUMN_INDEX,
	MATRIX_LEFT,
	MATRIX_RIGHT,
	VECTOR,
	SELECTED_ROW,
	SELECTED_COLUMN,
	SELECTED_ROWS,
	SELECTED_COLUMNS,
	VECTOR1,
	VECTOR2;
}
