package written;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import given.Employee;

public class EmployeeListIterator implements Iterator<Employee> {
	
	// fields
	private List<Employee> empList;
	private int curPos;

	public EmployeeListIterator(EmployeeDatabase ed) {
		this.empList = ed.empList;
		curPos = 0;
	}

	@Override
	public boolean hasNext() {
		return curPos < empList.size();
	}

	@Override
	public Employee next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		Employee result = empList.get(curPos);
		curPos++;
		return result;
	}
	
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
