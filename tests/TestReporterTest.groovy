import com.mgufron.ReporterProcessor
import com.mgufron.BuildChangeSet
import groovy.transform.SourceURI
import java.nio.file.Path
import java.nio.file.Paths

@SourceURI
URI sourceUri

Path scriptLocation = Paths.get(sourceUri)

def reporter = new ReporterProcessor(["${scriptLocation.getParent().getFileName()}/report.xml"])
reporter.generateReportSuite()
println "tests: ${reporter.getTotalTests()}"
println "failures: ${reporter.getFailures()}"
println "success: ${reporter.getSuccessful()}"
println "skipped: ${reporter.getSkipped()}"

def cs = new BuildChangeSet(["Jenkinsfile", "worker.Dockerfile", "services/jobs/cmd/worker/handlers/main.go"])
println "is match: ${cs.contains(["services/proposals/**/*.go", "worker.Dockerfile"])}"
