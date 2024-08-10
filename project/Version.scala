import scala.sys.process.Process


object Version {
    
    /**
     * Determine the current version based on git commits marked with “major:”, “minor:” or “patch:”.
     */
    def current: String = {
        val commits = Process("git --no-pager log --reverse --pretty=format:'%s'").lineStream.toList
        
        commits.foldLeft((0, 0, 0))((version, commit) =>
            commit.drop(1).takeWhile(x => x != '(' && x != ':') match {
                case "major" => (version._1 + 1, 0, 0)
                case "minor" => (version._1, version._2 + 1, 0)
                case "patch" => (version._1, version._2, version._3 + 1)
                case "admin" => (version._1, version._2, version._3)
                case unknown => throw new sbt.MessageOnlyException(
                    s"One of the commit messages begins with “$unknown” which is not a recognised commit type."
                )
            }
        ) match { case (major, minor, patch) => s"$major.$minor.$patch" }
    }
    
}
