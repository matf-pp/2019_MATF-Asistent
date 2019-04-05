package data

data class CourseListItem(
    var major: Repository.Major,
    var year: Int,
    var semester: Int,
    var title: String,
    var theoryClassCount: Int = 3,
    var practiceClassCount: Int = 3,
    var espbPoints: Int = 6
)