package foundly.restapi.exception;

/**
 * The Class ItemNotFoundException. Is thrown when requesting an Item which doesn't exist in the
 * item repository.
 */
@SuppressWarnings("serial")
public class ItemNotFoundException extends RuntimeException {

  /**
   * Instantiates a new item not found exception.
   *
   * @param id the requested id
   */
  public ItemNotFoundException(Long id) {
    super("Could not find item with id: " + id);
  }
}
