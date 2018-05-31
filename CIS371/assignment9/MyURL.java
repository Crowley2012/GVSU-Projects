/******************************************
 * CIS 371 Assignment 4
 *
 * Sean Crowley Vignesh Suresh
 *
 * This class splits up a web address
 ******************************************/

public class MyURL {
	private String scheme = "http";
	private String domainName = null;
	private int port = 80;
	private String path = "/";
	private String url = "";

	public MyURL(String url) {
		// Stores the original url
		this.url = url;
		getParts(url);
	}

	public MyURL(String newURL, MyURL currentURL) {
		// Stores the original url
		this.url = newURL;

		// Checks if the url has a scheme
		boolean hasScheme = false;
		if (!newURL.substring(0, 5).equals(scheme + ":")) {
			String temp = "";

			// Continues to add to string until a : is found
			// If : not found then scheme does not exist
			for (int i = 0; i < 8; i++) {
				String s = newURL.substring(i, i + 1);
				if (s.equals(":")) {
					scheme = temp;
					hasScheme = true;
					break;
				} else {
					temp += s;
				}
			}
		}

		// If a scheme exist then normal url function occurs
		if (hasScheme)
			getParts(newURL);
		// If not then url is appended
		else {
			currentURL.path += newURL;
			path = currentURL.path;
			domainName = currentURL.domainName;
		}
	}

	private void getParts(String url) {
		// Get the scheme
		if (!url.substring(0, 5).equals(scheme + ":")) {
			String temp = "";

			// Continues to add to string until a : is found
			// If : not found then scheme does not exist
			for (int i = 0; i < 8; i++) {
				String s = url.substring(i, i + 1);
				if (s.equals(":")) {
					scheme = temp;
					break;
				} else {
					temp += s;
				}
			}
		}

		int start = 0;
		int end = 0;

		// Gets the start of the domain
		if (url.indexOf("//") > 0)
			start = url.indexOf("//") + 2;

		// Gets the end of the domain
		if (url.substring(start).indexOf(":") > 0)
			end = url.substring(start).indexOf(":") + start;
		else if (url.substring(start).indexOf("/") > 0)
			end = url.substring(start).indexOf("/") + start;
		else
			end = url.length();

		// Gets the domain
		domainName = url.substring(start, end);

		// Get file path and port
		if (url.substring(8, url.length()).contains(":")) {
			int i = url.substring(8).indexOf(":") + 9;
			String s = "";

			while (i < url.length() && Character.isDigit(url.charAt(i))) {
				s += url.charAt(i);
				i++;
			}

			path = url.substring(i);
			port = Integer.parseInt(s);
		} else {
			path = url.substring(end, url.length());
		}
		
		//path = path.substring(0, path.lastIndexOf("/") + 1);

		// If empty url is given then replaced with /
		if (path.equals(""))
			path = "/";

	}
	
	public String getPath(){
		return path.substring(0, path.lastIndexOf("/") + 1);
	}

	public String scheme() {
		return scheme;
	}

	public String domainName() {
		return domainName;
	}

	public int port() {
		return port;
	}

	public String path() {
		return path;
	}

	public String toString() {
		return url + "/";
	}

	// Needed in order to use MyURL as a key to a HashMap
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	// Needed in order to use MyURL as a key to a HashMap
	@Override
	public boolean equals(Object other) {
		if (other instanceof MyURL) {
			MyURL otherURL = (MyURL) other;
			return this.scheme.equals(otherURL.scheme) && this.domainName.equals(otherURL.domainName) && this.port == otherURL.port() && this.path.equals(otherURL.path);
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		new MyURL("http://upload.wikimedia.org/wikipedia/commons/7/70/Logo_Apple.inc.gif");
	}
}