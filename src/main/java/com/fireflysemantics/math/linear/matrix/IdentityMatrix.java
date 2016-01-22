package com.fireflysemantics.math.linear.matrix;

import static com.fireflysemantics.math.exception.ExceptionFactory.checkIndexOutOfRange;
import static com.fireflysemantics.math.exception.ExceptionFactory.checkNotStrictlyPositive;
import static com.fireflysemantics.math.linear.exceptions.LinearExceptionKeys.SQUARE_DIMENSION;

import com.fireflysemantics.math.exception.MathException;
import com.fireflysemantics.math.linear.matrix.interfaces.Matrix;

import lombok.Getter;

public class IdentityMatrix implements Matrix {

	/**
	 * {@link interfaces.Matrix#getRowDimension}
	 * 
	 * @return the row dimension
	 */
	@Getter
	private int rowDimension;

	/**
	 * {@link interfaces.Matrix#getColumnDimension}
	 * 
	 * @return the column dimension
	 */
	@Getter
	private int columnDimension;

	/**
	 * Create a new ArrayMatrix with the supplied row and column dimensions. All
	 * matrix entries will be initialized to their java default of 0d.
	 *
	 * @param dimension
	 *            the number of rows and columns in the new matrix
	 * @throws MathException
	 *             Of type {code NOT_STRICTLY_POSITIVE} if the {@code dimension}
	 *             is not positive.
	 */
	public IdentityMatrix(final int dimension) throws MathException {
		checkNotStrictlyPositive(SQUARE_DIMENSION, dimension);
		this.rowDimension = dimension;
		this.columnDimension = dimension;
	}

	@Override
	public boolean isSquare() {
		return true;
	}

	@Override
	public double getEntry(int row, int column) throws MathException {
		checkIndexOutOfRange(row, 0, rowDimension);
		checkIndexOutOfRange(column, 0, columnDimension);
		if (row == column) {
			return 1d;
		}
		return 0d;
	}

	@Override
	public void setEntry(int row, int column, double value) throws MathException {
		// Do nothing. Diagnoal entries are one and all other are zero.
	}

	@Override
	public Matrix copy() {
		return this;
	}
}
