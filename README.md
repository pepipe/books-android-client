# BOOKS Android Client
## Test description - Book Store
Book Store consists on showing a simple 2-column list of available books about mobile development.
Using google’s api for books, the app should fetch and display the Thumbnail of a few books at a time and load more as the user scroll’s through the list.

- [Rest API](https://developers.google.com/books/docs/v1/getting_started#REST)
- [Example API Call](https://www.googleapis.com/books/v1/volumes?q=ios&maxResults=20&startIndex=0)

The list should also have a button to filter/show only books that the user has set as favorite.

When the user clicks on one of the books, the app should present a detailed view displaying the most relevant information of the book: Title, Author, Description and, if available, a Buy link.

In the detail view, the user can also favorite or “unfavorite” a book. This option should be stored locally so it persists through each app usage.

Clicking on the Buy link should open the link on safari/chrome.

This app must be build fallowing this rules:
1. All the API integrations must be built using C++.
    1. The app should call this api method and receive a callback with the API result (a json object or
an error).
2. An APP built using Objective-c/Java that import and use those C++ class.

It is very important show your experience with testing;

Nice to have: Favorites in local storage using C++

## Project Setup - Android
Minimum Android SDK set for the project is 26 (Android 8), Target SDK is 35 (Android 15).

## Project Architecture
### File Structure
com.example.booksclient
├── data
│   ├── network
│   │   └── model
│   │       ├── BookResponse.java
│   │       ├── GoogleBooksResponse.java
│   │       ├── ImageLinksResponse.java
│   │       ├── SaleInfoResponse.java
│   │       └── VolumeInfoResponse.java
│   └── repository
│       └── BookRepository.java
├── domain
│   └── model
│       └── Book.java
├── mapper
│   ├── BookMapper.java
│   ├── BooksParser.java
│   └── FavoriteBooksHelper.java
├── presentation
│   ├── book
│   │   ├── BookActivity.java
│   │   ├── BookAdapter.java
│   │   └── BookViewModel.java
│   └── booksDetails
│       ├── BookDetailsActivity.java
│       └── BookDetailsViewModel.java
├── utils
│   └── LogHelper.java
└── NativeApi.java

This project follows a clean architecture. I try to achieve the following points:

1. Separation of Concerns:
1.1. Data Layer: Handles all data-related operations (network calls, fetching data).
1.2. Domain Layer: Contains business logic (Book model).
1.3. Mapper Layer: Transforms data models between layers.
1.4. Presentation Layer: Manages UI components and their interactions with ViewModels.

2. Use of ViewModel and LiveData:
2.1. ViewModels (BookViewModel, BookDetailsViewModel) manage the data for UI components in a lifecycle-conscious way.
2.2. LiveData ensures that your UI is updated when the data changes.

3. Repository Pattern:
3.1. The BookRepository acts as a single source of truth for data operations, abstracting away the complexities of data handling.

4. Background Task Management:
4.1. Using ExecutorService to handle background tasks ensures that your UI remains responsive.

5. View Binding:
5.1. Improves code readability and reduces the need for findViewById calls.

### Improvements and Considerations:
Since this is a test, some things didn't get the deserved attention. 
Error handling, there is some in the project but not always give information to end user. 

No dependency injection was used, probably in a real scenario a library like Dagger or Hilt would be used to further decouple dependencies,
making the code more modular and testable.

Testing: Some unit tests were made only to showcase knowledge of testing.
