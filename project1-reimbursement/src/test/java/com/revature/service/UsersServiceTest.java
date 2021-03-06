package com.revature.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.FailedLoginException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.dao.UsersDAO;
import com.revature.exceptions.ReceiptNotFoundException;
import com.revature.exceptions.ReimbursementNotFoundExcpetion;
import com.revature.exceptions.UnauthorizedException;
import com.revature.models.Reimbursement;
import com.revature.models.Users;
import com.revature.services.UsersService;

class UsersServiceTest {

	private UsersService usersService;
	UsersDAO mockUsersDao;

	@BeforeEach
	public void setUp() {
		this.usersService = new UsersService();
		this.mockUsersDao = mock(UsersDAO.class);
	}

	/*
	 * getUsernameAndPassword() method test
	 */

	@Test // Happy Path
	void getUsernameAndPassword_PostiveTest() throws SQLException, FailedLoginException {

		Users user = new Users(1, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Finance Manager");

		when(mockUsersDao.selectUserByUsernameAndPassword(eq("jymm.enriquez"), eq("p4ssw0rd"))).thenReturn(user);

		usersService = new UsersService(mockUsersDao);

		Users actual = usersService.getUserByUsernameAndPassword("jymm.enriquez", "p4ssw0rd");

		Users expected = new Users(1, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Finance Manager");

		Assertions.assertEquals(expected, actual);
	}

	@Test // Sad Path
	void getUserNameAndPassword_NegativeTest_UsersReturnsNull() {

		usersService = new UsersService(mockUsersDao);

		Assertions.assertThrows(FailedLoginException.class, () -> {
			usersService.getUserByUsernameAndPassword("jymm.enriquez", "p4ssw0rd");
		});
	}

	/*
	 * gettAllReimbursement() test
	 */

	@Test // Only Path and the Only Happy Path :D
	void getAllReimbursement_Postive_Test() throws SQLException {

		Reimbursement firstReimb = new Reimbursement(1, 100.50, "2021-12-05 14:27:58", null, "Pending", "Lodging",
				"Duplicate Room rental", 2, 0);
		Reimbursement secondReimb = new Reimbursement(2, 12.50, "2021-12-05 14:28:24", "2021-12-05 14:29:22",
				"Approved", "Food", "Spoiled Food", 2, 1);

		List<Reimbursement> listOfAllReimbursements = new ArrayList<>();
		listOfAllReimbursements.add(firstReimb);
		listOfAllReimbursements.add(secondReimb);

		when(mockUsersDao.selectAllReimbursements()).thenReturn(listOfAllReimbursements);
		usersService = new UsersService(mockUsersDao);

		List<Reimbursement> actual = usersService.getAllReimbursement();

		List<Reimbursement> expected = new ArrayList<>();

		expected.add(new Reimbursement(1, 100.50, "2021-12-05 14:27:58", null, "Pending", "Lodging",
				"Duplicate Room rental", 2, 0));
		expected.add(new Reimbursement(2, 12.50, "2021-12-05 14:28:24", "2021-12-05 14:29:22", "Approved", "Food",
				"Spoiled Food", 2, 1));

		Assertions.assertEquals(expected, actual);

	}

	/*
	 * gettAllReimbursementById() test
	 */

	@Test // Only Path and the Only Happy Path :D
	void gettAllReimbursementById_PostiveTest() throws SQLException {

		Reimbursement firstReimb = new Reimbursement(1, 100.50, "2021-12-05 14:27:58", null, "Pending", "Lodging",
				"Duplicate Room rental", 3, 0); // <-- Second to the last int is the id, coming from user.getErsUserId()
		Reimbursement secondReimb = new Reimbursement(2, 12.50, "2021-12-05 14:28:24", "2021-12-05 14:29:22",
				"Approved", "Food", "Spoiled Food", 3, 1); // <-- Second to the last int is the id, coming from
															// user.getErsUserId()

		List<Reimbursement> listOfAllReimbursements = new ArrayList<>();
		listOfAllReimbursements.add(firstReimb);
		listOfAllReimbursements.add(secondReimb);

		when(mockUsersDao.selectAllReimbursementsById(eq(3))).thenReturn(listOfAllReimbursements);
		usersService = new UsersService(mockUsersDao);

		Users user = new Users(3, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Employee");

		List<Reimbursement> actual = usersService.getAllReimbursementById(user);

		List<Reimbursement> expected = new ArrayList<>();

		expected.add(new Reimbursement(1, 100.50, "2021-12-05 14:27:58", null, "Pending", "Lodging",
				"Duplicate Room rental", 3, 0)); // <-- Second to the last int is the id, coming from
													// user.getErsUserId()
		expected.add(new Reimbursement(2, 12.50, "2021-12-05 14:28:24", "2021-12-05 14:29:22", "Approved", "Food",
				"Spoiled Food", 3, 1)); // <-- Second to the last int is the id, coming from user.getErsUserId()

		Assertions.assertEquals(expected, actual);

	}

	/*
	 * editReimbursement() test
	 */

	@Test // Happy Path
	void editReimbursement_PostiveTest_AllFieldsValid()
			throws SQLException, ReimbursementNotFoundExcpetion, UnauthorizedException, IOException {

		Reimbursement editReimb = new Reimbursement(1, 100.50, "2021-12-05 14:27:58", null, "Pending", "Lodging",
				"Duplicate Room rental", 3, 0);

		when(mockUsersDao.selectReimbursementById(eq(1))).thenReturn(editReimb);
		when(mockUsersDao.updateReimbursement(eq(1), eq(1), eq(editReimb), eq("Approved")))
				.thenReturn(new Reimbursement(1, 100.50, "2021-12-05 14:27:58", "2021-12-05 14:27:58", "Approved",
						"Lodging", "Duplicate Room rental", 3, 1));
		usersService = new UsersService(mockUsersDao);

		Users user = new Users(1, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Finance Manager");

		Reimbursement actual = usersService.editReimbursement(user, "1", "Approved");

		Reimbursement expected = new Reimbursement(1, 100.50, "2021-12-05 14:27:58", "2021-12-05 14:27:58", "Approved",
				"Lodging", "Duplicate Room rental", 3, 1);

		Assertions.assertEquals(expected, actual);

	}

	@Test // Sad Path
	void editReimbursement_NegativeTest_NoExistingReimbursement() {

		// ReimbursementNotFoundExcpetion
		usersService = new UsersService(mockUsersDao);

		Users user = new Users(1, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Finance Manager");

		Assertions.assertThrows(ReimbursementNotFoundExcpetion.class, () -> {
			usersService.editReimbursement(user, "1", "Approved");
		});
	}

	@Test // Sad Path
	void editReimbursement_NegativeTest_CannotApproveOwnRequest_ForMultipleFinanceManagersInTheSystem()
			throws SQLException {

		// UnauthorizedException
		Reimbursement editReimb = new Reimbursement(1, 100.50, "2021-12-05 14:27:58", null, "Pending", "Lodging",
				"Duplicate Room rental", 1, 0);

		when(mockUsersDao.selectReimbursementById(eq(1))).thenReturn(editReimb);
		usersService = new UsersService(mockUsersDao);

		Users user = new Users(1, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Finance Manager");

		Assertions.assertThrows(UnauthorizedException.class, () -> {
			usersService.editReimbursement(user, "1", "Approved");
		});
	}

	@Test // Sad Path
	void editReimbursement_NegativeTest_AlreadyApprovedOrRejected_ForMultipleFinanceManagersInTheSystem()
			throws SQLException {

		// UnauthorizedException
		Reimbursement editReimb = new Reimbursement(1, 100.50, "2021-12-05 14:27:58", "2021-12-05 14:27:58", "Approved",
				"Lodging", "Duplicate Room rental", 3, 2);

		when(mockUsersDao.selectReimbursementById(eq(1))).thenReturn(editReimb);
		usersService = new UsersService(mockUsersDao);

		Users user = new Users(1, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Finance Manager");

		Assertions.assertThrows(UnauthorizedException.class, () -> {
			usersService.editReimbursement(user, "1", "Approved");
		});
	}

	/*
	 * getReceiptFromReimbursementById() test
	 */

	@Test // Happy Path
	void getReceiptFromReimbursementById_PostiveTest()
			throws SQLException, UnauthorizedException, ReimbursementNotFoundExcpetion, ReceiptNotFoundException {

		Users user = new Users(1, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Finance Manager");

		InputStream receipt = new ByteArrayInputStream("test data".getBytes());
		when(mockUsersDao.selectReceiptFromReimbursementById(eq(1), eq(user.getErsUserId()))).thenReturn(receipt);

		Reimbursement checkReimb = new Reimbursement(1, 100.50, "2021-12-05 14:27:58", "2021-12-05 14:27:58",
				"Approved", "Lodging", "Duplicate Room rental", 3, 1);

		when(mockUsersDao.selectReimbursementById(eq(1))).thenReturn(checkReimb);

		usersService = new UsersService(mockUsersDao);

		InputStream actual = usersService.getReceiptFromReimbursementById(user, "1");

		InputStream expected = receipt;
		Assertions.assertEquals(expected, actual);
	}

	@Test // Sad Path
	void getReceiptFromReimbursementById_NegativeTest_NoExistingReimbursement() throws SQLException {

		// ReimbursementNotFoundExcpetion
		usersService = new UsersService(mockUsersDao);

		Users user = new Users(1, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Finance Manager");

		Reimbursement checkReimb = new Reimbursement(1, 100.50, "2021-12-05 14:27:58", "2021-12-05 14:27:58",
				"Approved", "Lodging", "Duplicate Room rental", 3, 2);
		when(mockUsersDao.selectReimbursementById(eq(1))).thenReturn(checkReimb);

		Assertions.assertThrows(ReimbursementNotFoundExcpetion.class, () -> {
			usersService.getReceiptFromReimbursementById(user, "2");
		});
	}

	@Test // Sad Path
	void getReceiptFromReimbursementById_NegativeTest_ReceiptBelongsToAnotherFinanceManager() throws SQLException {

		// UnauthorizedException
		Users user = new Users(1, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Finance Manager");

		Reimbursement checkReimb = new Reimbursement(1, 100.50, "2021-12-05 14:27:58", "2021-12-05 14:27:58",
				"Approved", "Lodging", "Duplicate Room rental", 3, 2);
		when(mockUsersDao.selectReimbursementById(eq(1))).thenReturn(checkReimb);

		usersService = new UsersService(mockUsersDao);

		Assertions.assertThrows(UnauthorizedException.class, () -> {
			usersService.getReceiptFromReimbursementById(user, "1");
		});
	}

	@Test // Sad Path
	void getReceiptFromReimbursementById_NegativeTest_ReceiptDoesNotExist() throws SQLException {

		Users user = new Users(1, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Finance Manager");
		Reimbursement checkReimb = new Reimbursement(1, 100.50, "2021-12-05 14:27:58", "2021-12-05 14:27:58",
				"Approved", "Lodging", "Duplicate Room rental", 3, 1);
		when(mockUsersDao.selectReimbursementById(eq(1))).thenReturn(checkReimb);
		usersService = new UsersService(mockUsersDao);

		// InputStream receipt = null;

		Assertions.assertThrows(ReceiptNotFoundException.class, () -> {
			usersService.getReceiptFromReimbursementById(user, "1");
		});
	}

	/*
	 * getCustomerReceiptFromReimbursementById() test
	 */

	@Test // Happy Path
	void getCustomerReceiptFromReimbursementById_PostiveTest()
			throws SQLException, UnauthorizedException, ReimbursementNotFoundExcpetion, ReceiptNotFoundException {

		Users user = new Users(1, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Finance Manager");

		Reimbursement firstReimb = new Reimbursement(1, 100.50, "2021-12-05 14:27:58", null, "Pending", "Lodging",
				"Duplicate Room rental", 1, 0);
		Reimbursement secondReimb = new Reimbursement(2, 12.50, "2021-12-05 14:28:24", "2021-12-05 14:29:22",
				"Approved", "Food", "Spoiled Food", 1, 3);

		List<Reimbursement> reimbursementsThatBelongsToManager = new ArrayList<>();
		reimbursementsThatBelongsToManager.add(firstReimb);
		reimbursementsThatBelongsToManager.add(secondReimb);

		// this.userDao.selectAllReimbursements();
		when(mockUsersDao.selectAllReimbursements()).thenReturn(reimbursementsThatBelongsToManager);

		InputStream receipt = new ByteArrayInputStream("test data".getBytes());
		when(mockUsersDao.selectCustomerReceiptFromReimbursementById(eq(1))).thenReturn(receipt);

		usersService = new UsersService(mockUsersDao);

		InputStream actual = usersService.getCustomerReceiptFromReimbursementById(user, "1");

		InputStream expected = receipt;
		Assertions.assertEquals(expected, actual);
	}

	@Test // Sad Path
	void getCustomerReceiptFromReimbursementById_NegativeTest_UserNotAnEmployee() {

		Users user = new Users(2, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Finance Manager");

		usersService = new UsersService(mockUsersDao);

		Assertions.assertFalse(user.getErsRole().equals("Employee"));
	}

	@Test // Sad Path
	void getCustomerReceiptFromReimbursementById_NegativeTest_UserNotAFinanceManager() {

		Users user = new Users(2, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Employee");

		usersService = new UsersService(mockUsersDao);

		Assertions.assertFalse(user.getErsRole().equals("Finance Manager"));
	}

	@Test // Sad Path
	void getCustomerReceiptFromReimbursementById_NegativeTest_InvalidReimbId() {

		Users user = new Users(2, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Employee");

		usersService = new UsersService(mockUsersDao);

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			usersService.getCustomerReceiptFromReimbursementById(user, "string");
		});
	}

	@Test // Sad Path
	void getCustomerReceiptFromReimbursementById_NegativeTest_ReceiptDoesNotExist() throws SQLException {

		Users user = new Users(1, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Finance Manager");
		Reimbursement checkReimb = new Reimbursement(1, 100.50, "2021-12-05 14:27:58", "2021-12-05 14:27:58",
				"Approved", "Lodging", "Duplicate Room rental", 1, 3);

		// this.userDao.selectAllReimbursements();
		List<Reimbursement> reimbursementsThatBelongsToManager = new ArrayList<>();
		reimbursementsThatBelongsToManager.add(checkReimb);
		when(mockUsersDao.selectAllReimbursements()).thenReturn(reimbursementsThatBelongsToManager);
		usersService = new UsersService(mockUsersDao);

		// InputStream receipt = null;

		Assertions.assertThrows(ReceiptNotFoundException.class, () -> {
			usersService.getCustomerReceiptFromReimbursementById(user, "1");
		});
	}

	@Test // Sad Path
	void getCustomerReceiptFromReimbursementById_NegativeTest_ReceiptBelongsToAnotherEmployee() throws SQLException {

		Users user = new Users(2, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Employee");

		Reimbursement firstReimb = new Reimbursement(1, 100.50, "2021-12-05 14:27:58", null, "Pending", "Lodging",
				"Duplicate Room rental", 2, 0);
		Reimbursement secondReimb = new Reimbursement(2, 12.50, "2021-12-05 14:28:24", "2021-12-05 14:29:22",
				"Approved", "Food", "Spoiled Food", 2, 1);

		List<Reimbursement> reimbursementsThatBelongsToEmployee = new ArrayList<>();
		reimbursementsThatBelongsToEmployee.add(firstReimb);
		reimbursementsThatBelongsToEmployee.add(secondReimb);
		when(mockUsersDao.selectAllReimbursementsById(2)).thenReturn(reimbursementsThatBelongsToEmployee);

		usersService = new UsersService(mockUsersDao);

		Assertions.assertThrows(UnauthorizedException.class, () -> {
			usersService.getCustomerReceiptFromReimbursementById(user, "3");
		});
	}

	@Test // Sad Path
	void getCustomerReceiptFromReimbursementById_NegativeTest_ReceiptBelongsToAnotherFinanceManager()
			throws SQLException {

		Users user = new Users(2, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Finance Manager");

		Reimbursement firstReimb = new Reimbursement(15, 100.50, "2021-12-05 14:27:58", null, "Pending", "Lodging",
				"Duplicate Room rental", 2, 0);
		Reimbursement secondReimb = new Reimbursement(26, 12.50, "2021-12-05 14:28:24", "2021-12-05 14:29:22",
				"Approved", "Food", "Spoiled Food", 2, 1);

		List<Reimbursement> reimbursementsThatBelongsToFinanceManager = new ArrayList<>();
		reimbursementsThatBelongsToFinanceManager.add(firstReimb);
		reimbursementsThatBelongsToFinanceManager.add(secondReimb);
		when(mockUsersDao.selectAllReimbursements()).thenReturn(reimbursementsThatBelongsToFinanceManager);

		usersService = new UsersService(mockUsersDao);

		Assertions.assertThrows(UnauthorizedException.class, () -> {
			usersService.getCustomerReceiptFromReimbursementById(user, "3");
		});
	}

	/*
	 * newUser() method
	 */

	@Test // Happy Path
	void newUser_PostiveTest_AllFieldsValid() throws SQLException {
		/*
		 * Users(String ersUsername, String ersPassword, String ersFirstName, String
		 * ersLastName, String ersEmail, String ersRole)
		 */
		Users newUser = new Users("jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Finance Manager");

		when(mockUsersDao.insertNewUser(newUser)).thenReturn(new Users(1, "jymm.enriquez", "p4ssw0rd", "Jymm",
				"Enriquez", "jymm.enriquez@revature.net", "Finance Manager"));
		usersService = new UsersService(mockUsersDao);

		Users actual = usersService.newUser(newUser);
		Users expected = new Users(1, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Finance Manager");

		Assertions.assertEquals(expected, actual);
	}

	@Test // Sad Path
	void newUser_NegativeTest_UsernameBlank_AllFieldsValid() {
		// InvalidParameterException

		Users newUser = new Users("", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net", "Finance Manager");
		usersService = new UsersService(mockUsersDao);

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			usersService.newUser(newUser);
		});
	}

	@Test // Sad Path
	void newUser_NegativeTest_PasswordBlank_AllFieldsValid() {
		// InvalidParameterException

		Users newUser = new Users("jymm.enriquez", "", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Finance Manager");
		usersService = new UsersService(mockUsersDao);

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			usersService.newUser(newUser);
		});
	}

	@Test // Sad Path
	void newUser_NegativeTest_FirstNameBlank_AllFieldsValid() {
		// InvalidParameterException

		Users newUser = new Users("jymm.enriquez", "p4ssw0rd", "", "Enriquez", "jymm.enriquez@revature.net",
				"Finance Manager");
		usersService = new UsersService(mockUsersDao);

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			usersService.newUser(newUser);
		});
	}

	@Test // Sad Path
	void newUser_NegativeTest_LastNameBlank_AllFieldsValid() {
		// InvalidParameterException

		Users newUser = new Users("jymm.enriquez", "p4ssw0rd", "Jymm", "", "jymm.enriquez@revature.net",
				"Finance Manager");
		usersService = new UsersService(mockUsersDao);

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			usersService.newUser(newUser);
		});
	}

	@Test // Sad Path
	void newUser_NegativeTest_EmailBlank_AllFieldsValid() {
		// InvalidParameterException
		Users newUser = new Users("jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "", "Finance Manager");
		usersService = new UsersService(mockUsersDao);

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			usersService.newUser(newUser);
		});
	}

	@Test // Sad Path
	void newUser_NegativeTest_RoleBlank_AllFieldsValid() {
		// InvalidParameterException

		Users newUser = new Users("jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net", "");
		usersService = new UsersService(mockUsersDao);

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			usersService.newUser(newUser);
		});
	}
	
	@Test // Sad Path
	void newUser_NegativeTest_RoleNotFinanceManagerOrEmployeeAndAllFieldsValid() {
		// InvalidParameterException

		Users newUser = new Users("jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net", "Guest");
		usersService = new UsersService(mockUsersDao);

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			usersService.newUser(newUser);
		});
	}
	/*
	 * getAllReimbursementByStatus() test
	 */

	@Test // Happy Path
	void getAllReimbursementByStatus_PostiveTest_AllPending() throws SQLException {
		
		Reimbursement firstReimb = new Reimbursement(1, 100.50, "2021-12-05 14:27:58", null, "Pending", "Lodging",
				"Duplicate Room rental", 2, 0);
		Reimbursement secondReimb = new Reimbursement(2, 23.20, "2021-12-10 11:20:33", null, "Pending", "Food",
				"Spoiled", 2, 0);


		List<Reimbursement> listOfAllReimbursements = new ArrayList<>();
		listOfAllReimbursements.add(firstReimb);
		listOfAllReimbursements.add(secondReimb);
		
		when(mockUsersDao.selectAllReimbursementsByStatus("Pending")).thenReturn(listOfAllReimbursements);
		
		usersService = new UsersService(mockUsersDao);
		
		List<Reimbursement> actual = usersService.getAllReimbursementByStatus("Pending");
		
		List<Reimbursement> expected = new ArrayList<>();
		
		expected.add(new Reimbursement(1, 100.50, "2021-12-05 14:27:58", null, "Pending", "Lodging",
				"Duplicate Room rental", 2, 0));
		expected.add(new Reimbursement(2, 23.20, "2021-12-10 11:20:33", null, "Pending", "Food",
				"Spoiled", 2, 0));
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test // Sad Path
	void getAllReimbursementByStatus_NegativeTest_StatusIsInvalid() {
		
		usersService = new UsersService(mockUsersDao);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			usersService.getAllReimbursementByStatus("Pendding");
		});
	}

	/*
	 * newReimbursement() test
	 */
	
	@Test // Happy Path
	
	void newReimbursement_PositiveTest_AllFieldsValid() throws SQLException {
		
		Users user = new Users(1, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Employee");	
		Reimbursement newReimb = new Reimbursement(1, 100.50, "2021-12-05 14:27:58", null, "Pending", "Lodging",
				"Duplicate Room rental", 2, 0);
		
		InputStream receipt = new ByteArrayInputStream("test data".getBytes());
		
		when(mockUsersDao.insertNewReimbursement(user.getErsUserId(), 100.50, "Lodging", "Duplicate Room rental", receipt))
		.thenReturn(newReimb);
		
		usersService = new UsersService(mockUsersDao);
		
		Reimbursement actual = usersService.newReimbursement(user, "100.50", "Lodging", "Duplicate Room rental", "image/jpeg", receipt);
		
		Reimbursement expected = new Reimbursement(1, 100.50, "2021-12-05 14:27:58", null, "Pending", "Lodging",
				"Duplicate Room rental", 2, 0);
		
		Assertions.assertEquals(expected, actual);	
	}
	
	@Test // Sad Path
	void newReimbursement_NegativeTest_AmountIsZero_AllFieldsValid() {
		//InvalidParameterException
		
		Users user = new Users(1, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Employee");	
		InputStream receipt = new ByteArrayInputStream("test data".getBytes());
		usersService = new UsersService(mockUsersDao);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			usersService.newReimbursement(user, "0", "Lodging", "Duplicate Room rental", "image/jpeg", receipt);
		});	
	}
	
	@Test // Sad Path
	void newReimbursement_NegativeTest_AmountIsNegativeNumber_AllFieldsValid() {
		//InvalidParameterException
		
		Users user = new Users(1, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Employee");	
		InputStream receipt = new ByteArrayInputStream("test data".getBytes());
		usersService = new UsersService(mockUsersDao);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			usersService.newReimbursement(user, "-1", "Lodging", "Duplicate Room rental", "image/jpeg", receipt);
		});	
	}
	
	@Test // Sad Path
	void newReimbursement_NegativeTest_AmountIsEmpty_AllFieldsValid() {
		//InvalidParameterException
		
		Users user = new Users(1, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Employee");	
		InputStream receipt = new ByteArrayInputStream("test data".getBytes());
		usersService = new UsersService(mockUsersDao);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			usersService.newReimbursement(user, "", "Lodging", "Duplicate Room rental", "image/jpeg", receipt);
		});	
	}
	
	@Test // Sad Path
	void newReimbursement_NegativeTest_MimeTypeIsInvalid_AllFieldsValid() {
		//InvalidParameterException
		
		Users user = new Users(1, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Employee");	
		InputStream receipt = new ByteArrayInputStream("test data".getBytes());
		usersService = new UsersService(mockUsersDao);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			usersService.newReimbursement(user, "100.50", "Lodging", "Duplicate Room rental", "text/html", receipt);
		});	
	}
	
	@Test // Sad Path
	void newReimbursement_NegativeTest_reimbTypeIsInvalid_AllFieldsValid() {
		//InvalidParameterException
		//reimbType must be only Food, Lodging, Travel, Other
		
		Users user = new Users(1, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Employee");	
		InputStream receipt = new ByteArrayInputStream("test data".getBytes());
		usersService = new UsersService(mockUsersDao);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			usersService.newReimbursement(user, "100.50", "Nani", "Duplicate Room rental", "image/jpeg", receipt);
		});	
	}
	
	@Test // Sad Path
	void newReimbursement_NegativeTest_reimbTypeIsEmpty_AllFieldsValid() {
		//InvalidParameterException
		//reimbType must be only Food, Lodging, Travel, Other
		
		Users user = new Users(1, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Employee");	
		InputStream receipt = new ByteArrayInputStream("test data".getBytes());
		usersService = new UsersService(mockUsersDao);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			usersService.newReimbursement(user, "100.50", "", "Duplicate Room rental", "image/jpeg", receipt);
		});	
	}
	
	@Test // Sad Path
	void newReimbursement_NegativeTest_reimbDescriptionIsEmpty_AllFieldsValid() {
		//InvalidParameterException
		
		Users user = new Users(1, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez", "jymm.enriquez@revature.net",
				"Employee");	
		InputStream receipt = new ByteArrayInputStream("test data".getBytes());
		usersService = new UsersService(mockUsersDao);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			usersService.newReimbursement(user, "100.50", "Food", "", "image/jpeg", receipt);
		});	
	}
	
	/*
	 * getAReimbursementById() test
	 */
	
	@Test // Happy Path
	void getAReimbursementById_PositiveTest() throws SQLException, ReimbursementNotFoundExcpetion {
		
		Reimbursement newReimb = new Reimbursement(1, 100.50, "2021-12-05 14:27:58", null, "Pending", "Lodging",
				"Duplicate Room rental", 2, 0);
		
		when(mockUsersDao.selectReimbursementById(1)).thenReturn(newReimb);
		
		usersService = new UsersService(mockUsersDao);
		
		Reimbursement actual = usersService.getAReimbursementById("1");
		
		Reimbursement expected = new Reimbursement(1, 100.50, "2021-12-05 14:27:58", null, "Pending", "Lodging",
				"Duplicate Room rental", 2, 0);
		
		Assertions.assertEquals(expected, actual);	
	}
	
	@Test // Sad Path
	void getAReimbursementById_NegativeTest_IdIsNotAnInteger() {
		
		usersService = new UsersService(mockUsersDao);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			usersService.getAReimbursementById("String");
		});
	}

	@Test // Sad Path
	void getAReimbursementById_NegativeTest_ReimbursementNotFound() {
		
		usersService = new UsersService(mockUsersDao);
		
		Assertions.assertThrows(ReimbursementNotFoundExcpetion.class, () -> {
			usersService.getAReimbursementById("1");
		});
	}
}
