package io.oasp.gastronomy.restaurant.tablemanagement.service.impl.rest;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.mmm.util.exception.api.ObjectNotFoundUserException;

import org.owasp.appsensor.core.DetectionPoint;
import org.owasp.appsensor.core.DetectionSystem;
import org.owasp.appsensor.core.Event;
import org.owasp.appsensor.core.IPAddress;
import org.owasp.appsensor.core.User;
import org.owasp.appsensor.core.configuration.client.ClientConfiguration;
import org.owasp.appsensor.core.geolocation.GeoLocation;
import org.owasp.appsensor.event.RestEventManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.oasp.gastronomy.restaurant.tablemanagement.common.api.Table;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.Tablemanagement;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

/**
 * The service class for REST calls in order to execute the methods in {@link Tablemanagement}.
 *
 * @author agreul
 */
@Path("/tablemanagement/v1")
@Named("TablemanagementRestService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Configuration
@ComponentScan("org.owasp.appsensor.event")
public class TablemanagementRestServiceImpl {

  private DetectionSystem gastronomySense = new DetectionSystem("gastronomySense");

  private DetectionPoint test;

  @Autowired
  RestEventManager eventManager;

  @Autowired
  ClientConfiguration configuration;

  /*
   * @Autowired private AppSensorClient appSensorClient;
   */

  private Tablemanagement tableManagement;

  /**
   * This method sets the field <tt>tableManagement</tt>.
   *
   * @param tableManagement the new value of the field tableManagement
   */
  @Inject
  public void setTableManagement(Tablemanagement tableManagement) {

    this.tableManagement = tableManagement;
  }

  // @Autowired
  // public void setRestEventManager(RestEventManager eventManager) {
  //
  // this.eventManager = eventManager;
  // }
  /*
   * private DetectionSystem getDetectionSystem() {
   * 
   * return new DetectionSystem(
   * this.appSensorClient.getConfiguration().getServerConnection().getClientApplicationIdentificationHeaderValue()); }
   */

  public TableEto getTable(String id) {

    long idAsLong;
    if (id.toLowerCase().contains("insert")) {
      this.test = new DetectionPoint(DetectionPoint.Category.INPUT_VALIDATION, "IE1");
      // TODO: JSON event typ
      // warum keine Fehler?
      // Überprüfen
      User bob = new User("bob", new IPAddress("10.10.10.1", new GeoLocation(37.596758, -121.647992)));
      // User user = new User(getUserName());
      Event event = new Event(bob, this.test, this.gastronomySense);
      this.eventManager.addEvent(event);
    }
    if (id == null) {
      throw new BadRequestException("missing id");
    }
    try {
      idAsLong = Long.parseLong(id);
    } catch (NumberFormatException e) {
      throw new BadRequestException("id is not a number");
    } catch (NotFoundException e) {
      throw new BadRequestException("table not found");
    }
    return this.tableManagement.findTable(idAsLong);
  }

  @Deprecated
  public List<TableEto> getAllTables() {

    List<TableEto> allTables = this.tableManagement.findAllTables();
    return allTables;
  }

  @Deprecated
  public TableEto createTable(TableEto table) {

    return this.tableManagement.saveTable(table);
  }

  public TableEto saveTable(TableEto table) {

    return this.tableManagement.saveTable(table);
  }

  public void deleteTable(long id) {

    this.tableManagement.deleteTable(id);
  }

  @Deprecated
  public List<TableEto> getFreeTables() {

    return this.tableManagement.findFreeTables();
  }

  public boolean isTableReleasable(long id) {

    TableEto table = this.tableManagement.findTable(id);
    if (table == null) {
      throw new ObjectNotFoundUserException(Table.class, id);
    } else {
      return this.tableManagement.isTableReleasable(table);
    }
  }

  public PaginatedListTo<TableEto> findTablesByPost(TableSearchCriteriaTo searchCriteriaTo) {

    return this.tableManagement.findTableEtos(searchCriteriaTo);
  }
}
