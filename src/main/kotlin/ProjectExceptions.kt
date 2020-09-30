class NoteAlreadyExistException(message: String): RuntimeException(message)

class NoteNotExistException(message: String): RuntimeException(message)

class NoteIsDeletedException(message: String): RuntimeException(message)

class CommentAlreadyExistException(message: String): RuntimeException(message)

class CommentNotExistException(message: String): RuntimeException(message)

class CommentIsDeletedException(message: String): RuntimeException(message)