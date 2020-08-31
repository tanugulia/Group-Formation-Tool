package CSCI5308.GroupFormationTool.Common;

public final class DomainConstants {

    public static final int newStudents = 0;
    public static final int oldStudents = 1;
    public static final int badData = 2;
    public static final String smtpHost = "smtp.gmail.com";
    public static final int smtpPort = 587;
    public static final String mailUserName = "noreply.group22@gmail.com";
    public static final String mailPassword = "dalhousiemacs";
    public static final String domainUrl = "https://formgroups22-prod.herokuapp.com";
    public static final String forgotPasswordSubject = "Complete Password Reset!";
    public static final String forgotPasswordText = "To reset your password, follow this link: ";
    public static final String mailSentSuccess = "An email with reset link has been successfully sent!";
    public static final String passwordResetMessage = "Your password has been reset!";
    public static final String userDoesNotExists = "An account with [[emailId]] not found";
    public static final String tokenExpiredMessage = "The renew password link has expired, please renew it again";
    public static final String registrationSubject = "New Student Registration!";
    public static final int numeric = 1;
    public static final int MCQOne = 2;
    public static final int MCQMultiple = 3;
    public static final int freeText = 4;
    public final static long invalidData = 0;
    public final static long sqlError = -1;
    public static final String URL = "jdbc:mysql://db-5308.cs.dal.ca:3306/CSCI5308_22_PRODUCTION?useSSL=false" +
            "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public static final String USER = "CSCI5308_22_PRODUCTION_USER";
    public static final String PASSWORD = "CSCI5308_22_PRODUCTION_22986";
    public static final String instructorAddSuccess = "Instructor successfully added";
    public static final String instructorAddFailure = "Instructor could not be added";
    public static final String taAddSuccess = "added";
    public static final String taAddFailure = "not added";
    public static final String addCourseSuccess = "The course was successfully added!";
    public static final String addCourseFailure = "The course could not be added since the course "
            + "already exists!";
    public static final String deleteCourseSuccess = "The course was successfully deleted!";
    public static final String deleteCourseFailure = "The course could not be deleted since the course is used by a "
            + "student/TA/instructor.";
    public static final String invalidFile = "Please select a CSV file to upload.";
    public static final String csvFileProcessingError = "An error occurred while processing the CSV file.";
    public static final String error = "Something went wrong. Please try again!";
    public static final String signupInvalidDetails = "One or more mandatory fields are not entered";
    public static final String userAlreadyExists = "An account with [[emailId]] already exists.";
    public static final String passwordsDontMatch = "The passwords do not match. Please try again!";
    public static final String passwordHistoryMessage = "Your new password cannot be same as previous "
            + "[[history]] passwords!";
    public static final String passwordHistory = "Password History";
    public static final String passwordMinimumLength = "Minimum length of password should be ";
    public static final String passwordMaximumLength = "Maximum length of password should be ";
    public static final String passwordUppercaseMinimumLength = "Minimum number of uppercase characters "
            + "in password should be ";
    public static final String passwordLowercaseMinimumLength = "Minimum number of lowercase characters "
            + "in password should be ";
    public static final String passwordSpecialSymbolsMinimumLength = "Minimum number of symbols or special "
            + "characters in password should be ";
    public static final String passwordCharactersNotAllowed = " not allowed in password ";
    public static final String guestRole = "Guest";
    public static final String tARole = "TA";
    public static final String instructorRole = "Instructor";
    public static final String studentRole = "Student";
    public static final String AdminRole = "ADMIN";
    public static final String UserRole = "USER";
    public static final String surveySuccess = "The survey has been submitted successfully!";
    public static final String dbError = "There was an error while submitting the survey. Please try again in an hour.";
    public static int surveyNotCreated = 1;
    public static int surveyNotPublished = 2;
    public static int surveyNotHavingAlgorithm = 3;
    public static int surveyGroupFormationPossible = 4;
    public static String lessThanX = "lessThanX";
    public static String greaterThanX = "greaterThanX";
    public static Double maximumDistance = 999.0;
    public static Double minimumDistance = -999.0;
    public static int isSimilar = 1;
    public static int factor = 2;
    public static double maximumProbability = 1.0;
    public static double minimumProbability = 0.0;
    public static Integer initialXValues = 0;
    public static double probabilityFactor = 0.1;
    public static double thresholdProbability = 0.5;
    public static double distanceFactor = 10.0;
    public static int minimumMatchWords = 0;
    public static int thresholdStudentCount = 2;
    public static int minimumStudentCount = 1;
    public static String defaultOptionValue = "0";
    public static int iteratorThreshold = 2;
    public static String regexForResponse = "_";
    public static int invalidSurveyId = -1;
}
