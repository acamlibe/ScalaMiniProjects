import java.net.{URI, URL}
import java.util.UUID
import scala.annotation.tailrec
import scala.collection.immutable.HashMap
import scala.io.StdIn.readLine
import scala.util.{Try, Failure, Success}

@main def main(): Unit =
  println("---URL Shortener---")

  println("Commands: \n " +
    "\t generate (g) - generate a shortened URL \n " +
    "\t list (l) - show all shortened URLs \n" +
    "\t quit (q) - quit application")

  mainLoop(HashMap.empty[String, String])

@tailrec
def mainLoop(mappedUrls: HashMap[String, String]): Unit = {
  print("Command: ")
  val input = readLine()

  val updatedMap = input match {
    case "generate" | "g" | "G" => generateUrl(mappedUrls)
    case "list" | "l" | "L" =>
      listUrls(mappedUrls)
      mappedUrls
    case "quit" | "q" | "Q" =>
      quit()
      mappedUrls
    case _ => mappedUrls
  }

  mainLoop(updatedMap)
}

def generateUrl(mappedUrls: HashMap[String, String]): HashMap[String, String] = {
  print("Input URL: ")
  val url = readLine()

  Try(new URI(url).toURL) match {
    case Success(value) =>
      val guid = UUID.randomUUID().toString.replace("-", "")
      val shortened = f"https://blahblah.com/$guid"
      println(f"Shortened: $shortened")

      mappedUrls + (url -> shortened)
    case Failure(_) =>
      println("Invalid URL...")
      mappedUrls
  }
}

def listUrls(mappedUrls: HashMap[String, String]): Unit = {
  mappedUrls.foreach((url, shortened) => println(s"URL: $url | Shortened: $shortened"))
}

def quit(): Unit = {
  println("Exiting...")
  sys.exit(0)
}
