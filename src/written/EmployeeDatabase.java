package written;
import java.util.*;
import given.Employee;


public class EmployeeDatabase {
	
	ArrayList<Employee> empList = new ArrayList<Employee>(); // ArrayList of employees in this database
	
	// constructor
	public EmployeeDatabase() {
		// Create empty database
	}
	
	// public methods required by assignment
	public void addEmployee(String e) throws IllegalArgumentException {
		// Adds a new given.Employee to the database
		// Null Employees are invalid.
		Employee newEmployee = null;
		if (e == null) {
			throw new IllegalArgumentException();
		}
		else {
			newEmployee = new Employee(e);
			empList.add(newEmployee);
		}
	}
	
	public void addDestination(String e, String d) throws IllegalArgumentException {
		// Add the given destination d to the wish list for employee e in the database.
		// If employee e is not in the database throw a java.lang.IllegalArgumentException. If d is already in the wish list for employee e, just return.

		if (this.containsEmployee(e) == false) {
			throw new IllegalArgumentException();
		}
		else {
			Employee tempEmployee = this.retreiveEmployee(e);
			ArrayList<String> tempDests = (ArrayList<String>) tempEmployee.getWishlist();
			tempDests.add(d);
		}
	}
	
	public boolean containsEmployee(String e){
		// 	Return true if and only if employee e is in the database
		Employee tempEmployee = this.retreiveEmployee(e);
		if (tempEmployee == null) {
			return false;
		}
		return true;

	}
	public boolean containsDestination(String d) {
		// 	Return true if and only if destination d appears in at least one employee's wish list in the database.
		Iterator<Employee> empIter = this.iterator();
		while (empIter.hasNext()) {
			Employee tempEmp = empIter.next();
			if (this.hasDestination(tempEmp.getUsername(), d)) {
				return true;
			}
		}
		return false;
	}
	public boolean hasDestination(String e, String d) {
		// Returns true if and only if destination d is in the wish list for employee e.
		// If employee e is not in the database, return false.
		if (this.containsEmployee(e)) {
			Employee tempEmp = this.retreiveEmployee(e);
			ArrayList<String> empDests = (ArrayList<String>) tempEmp.getWishlist();
			Iterator<String> destIter = empDests.iterator();
			while (destIter.hasNext()) {
				String dest= destIter.next();
				if (dest.equals(d)) {
					return true;
				}
			}
		}
		return false;
	}
	public List<String> getEmployees(String d) {
		// Return the list of employees who have destination d in their wish list.
		// If destination d is not in the database, return a null list.
		ArrayList<String> listEmployees = new ArrayList<String>();
		Iterator<Employee> empIter = this.iterator();
		while (empIter.hasNext()) {
			Employee tempEmp = empIter.next();
			ArrayList<String> empWl = (ArrayList<String>) tempEmp.getWishlist();
			if (empWl.contains(d)) {
				listEmployees.add(tempEmp.getUsername());
				}
			}
		return listEmployees;
	}
	public List<String> getDestinations(String e){
		// Return the wish list for the employee e.
		// If an employee e is not in the database, return null
		Employee tempEmp = this.retreiveEmployee(e);
		if (tempEmp.getUsername().isEmpty()) {
			return null;
		}
		return tempEmp.getWishlist();
	}
	public Iterator<Employee> iterator(){
		// Return an Iterator over the Employee objects in the database.
		// The employees should be returned in the order they were added to the database (resulting from the order in which they are in the text file).
		return new EmployeeListIterator(this);
	}
	public boolean removeEmployee(String e){
		// 	Remove employee e from the database.
		// If employee e is not in the database, return false; otherwise (i.e., the removal is successful) return true.
		return this.empList.remove(this.retreiveEmployee(e));
	}
	
	public boolean removeDestination(String d){
		// 	Remove destination d from the database, i.e., remove destination d from every wish list in which it appears.
		// If destination d is not in the database, return false; otherwise (i.e., the removal is successful) return true.
		Boolean destInDb = false;
		Iterator<Employee> empIter = this.iterator();
		while (empIter.hasNext()) {
			Employee tempEmp = empIter.next();
			if (this.removeWlForEmp(tempEmp, d)) {
				destInDb = true;
			}
		}
		return destInDb;
	}
	public int size(){
		// Return the number of employees in this database.
		return this.empList.size();
	}
	
	// private methods
	private Employee retreiveEmployee(String e) throws IllegalArgumentException {
		Iterator<Employee> empIter = this.iterator();
		while (empIter.hasNext()) {
			Employee tempEmp = empIter.next();
			if (tempEmp.getUsername().equals(e)) {
				return tempEmp;
			}
		}
		return null;
	}
	
	private Boolean removeWlForEmp (Employee e, String d) {
		ArrayList<String> empWl = (ArrayList<String>) e.getWishlist();
		if (empWl.isEmpty() || !empWl.contains(d)) {
			return false;
		}
		Iterator<String> wlIter = empWl.iterator();
		while (wlIter.hasNext()) {
			String s = wlIter.next();
			if (s.equals(d)) {
				wlIter.remove();
			}
		}
		return true;
	}
}
