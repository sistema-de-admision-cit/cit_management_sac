-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_logs_score`
-- -----------------------------------------------------
-- id: unique identifier for each log entry
-- process_id: identifier to group logs related to the same process
-- tracktest_id: reference to the TrackTest exam
-- enrollment_id: reference to the enrollment
-- previous_score: previous score before the update
-- new_score: new score after the update
-- exam_date: date when the exam was taken
-- timestamp: date and time of the update
-- status: status of the update (success, error, warning)
-- error_message: error message in case of an error
DROP TABLE IF EXISTS tbl_logsscore;
CREATE TABLE tbl_logsscore (
  id INT PRIMARY KEY AUTO_INCREMENT,
  process_id INT,
  tracktest_id INT,
  enrollment_id INT,
  previous_score INT,
  new_score INT,
  exam_date DATE,
  timestamp TIMESTAMP,
  status ENUM('success', 'error', 'warning'),
  error_message TEXT DEFAULT NULL
);