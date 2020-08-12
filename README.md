# LatTex-Editor
An application for editing LaTex files.

## User Stories
1. As a user, I want to create a new Latex document, based on a particular Latex document template. If I don't specify a template, the application should create an empty Latex document.

2. As a user, I want to edit the contents of the Latex document, via the application's user interface.

3. As a user, I want to add Latex commands in the Latex document automatically using the application user interface. A minimal set of Latex commands that I want to use is given in Table 1. Some of the commands are allowed only for specific types of Latex documents. The application should notify me if I try to add Latex commands that are not allowed in the Latex document.

4. As a user, I should be able to activate an automatic version tracking mechanism that keeps track of the document evolution history, at any time. The history consists of a sequence of subsequent versions of the Latex document. The mechanism should provide at least two alternative storage strategies for the document evolution history:

    - Volatile (default strategy): for each document change the mechanism keeps the previous version of the document in a main memory list of subsequent document versions.

    - Stable: for each document change the mechanism keeps the previous version of the document on disk storage.

5. As a user, I should be able to change the storage strategy of the version tracking mechanism at any time.

    - If I change from Volatile to Stable the application should store the document evolution history on disk storage.

    - If I change from Stable to Volatile the application should load the document evolution history from disk storage to main memory.

6. As a user, I should be able to disable the version tracking mechanism at any time.

7. As a user, I should be able to rollback to a previous version of the Latex document based on the document evolution history. The application should notify me if I try to use the rollback action and the version tracking mechanism has not been enabled before.

8. As a user, I should be able to save the Latex document on disk storage.

9. As a user, I should be able to load the Latex document from disk storage.

## Architecture 
We use the Model View Controller (M.V.C.) architecture to seperate the classes by their responsibilities.
* Model classes implement the domain relevant data classes and update the Controller upon changes in the data.
* View classes implement the Graphic User Interface and display the Model data to the user.
* Controller implements the communication between Model and View. It requests the data from Model and supplies it to the Graphic User Interface.

## Design Patterns
* Prototype Pattern for creating LaTex documents using multiple templates.
* Strategy Pattern for implementing the different version tracking strategies.
* Command Pattern for performing all the different document opperations.

## Testing
We have implemented JUnit Tests for each User Story.

## Class Diagram
![Image](https://github.com/VictorMegir/LatTex-Editor/blob/master/UML.jpg)
