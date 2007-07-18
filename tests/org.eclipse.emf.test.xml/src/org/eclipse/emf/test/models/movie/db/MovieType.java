/**
 * <copyright>
 * </copyright>
 *
 * $Id: MovieType.java,v 1.1.2.1 2005/01/14 22:56:18 nickb Exp $
 */
package org.eclipse.emf.test.models.movie.db;

import java.util.List;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

import org.eclipse.emf.test.models.customer.CustomerType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Movie Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.test.models.movie.db.MovieType#getTitle <em>Title</em>}</li>
 *   <li>{@link org.eclipse.emf.test.models.movie.db.MovieType#getActors <em>Actors</em>}</li>
 *   <li>{@link org.eclipse.emf.test.models.movie.db.MovieType#getDirector <em>Director</em>}</li>
 *   <li>{@link org.eclipse.emf.test.models.movie.db.MovieType#getGenre <em>Genre</em>}</li>
 *   <li>{@link org.eclipse.emf.test.models.movie.db.MovieType#getSummary <em>Summary</em>}</li>
 *   <li>{@link org.eclipse.emf.test.models.movie.db.MovieType#getCriticsReviewGroup <em>Critics Review Group</em>}</li>
 *   <li>{@link org.eclipse.emf.test.models.movie.db.MovieType#getCriticsReview <em>Critics Review</em>}</li>
 *   <li>{@link org.eclipse.emf.test.models.movie.db.MovieType#getCheckedOutBy <em>Checked Out By</em>}</li>
 *   <li>{@link org.eclipse.emf.test.models.movie.db.MovieType#getAny <em>Any</em>}</li>
 *   <li>{@link org.eclipse.emf.test.models.movie.db.MovieType#getID <em>ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.test.models.movie.db.DbPackage#getMovieType()
 * @model 
 * @generated
 */
public interface MovieType extends EObject
{
  /**
   * Returns the value of the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Title</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Title</em>' attribute.
   * @see #setTitle(String)
   * @see org.eclipse.emf.test.models.movie.db.DbPackage#getMovieType_Title()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   * @generated
   */
  String getTitle();

  /**
   * Sets the value of the '{@link org.eclipse.emf.test.models.movie.db.MovieType#getTitle <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Title</em>' attribute.
   * @see #getTitle()
   * @generated
   */
  void setTitle(String value);

  /**
   * Returns the value of the '<em><b>Actors</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Actors</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Actors</em>' attribute.
   * @see #setActors(List)
   * @see org.eclipse.emf.test.models.movie.db.DbPackage#getMovieType_Actors()
   * @model unique="false" dataType="org.eclipse.emf.test.models.movie.db.ActorsList" required="true" many="false"
   * @generated
   */
  List getActors();

  /**
   * Sets the value of the '{@link org.eclipse.emf.test.models.movie.db.MovieType#getActors <em>Actors</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Actors</em>' attribute.
   * @see #getActors()
   * @generated
   */
  void setActors(List value);

  /**
   * Returns the value of the '<em><b>Director</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Director</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Director</em>' attribute.
   * @see #setDirector(String)
   * @see org.eclipse.emf.test.models.movie.db.DbPackage#getMovieType_Director()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   * @generated
   */
  String getDirector();

  /**
   * Sets the value of the '{@link org.eclipse.emf.test.models.movie.db.MovieType#getDirector <em>Director</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Director</em>' attribute.
   * @see #getDirector()
   * @generated
   */
  void setDirector(String value);

  /**
   * Returns the value of the '<em><b>Genre</b></em>' attribute.
   * The default value is <code>"NewRelease"</code>.
   * The literals are from the enumeration {@link org.eclipse.emf.test.models.movie.db.GenreTypes}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Genre</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Genre</em>' attribute.
   * @see org.eclipse.emf.test.models.movie.db.GenreTypes
   * @see #isSetGenre()
   * @see #unsetGenre()
   * @see #setGenre(GenreTypes)
   * @see org.eclipse.emf.test.models.movie.db.DbPackage#getMovieType_Genre()
   * @model default="NewRelease" unique="false" unsettable="true" required="true"
   * @generated
   */
  GenreTypes getGenre();

  /**
   * Sets the value of the '{@link org.eclipse.emf.test.models.movie.db.MovieType#getGenre <em>Genre</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Genre</em>' attribute.
   * @see org.eclipse.emf.test.models.movie.db.GenreTypes
   * @see #isSetGenre()
   * @see #unsetGenre()
   * @see #getGenre()
   * @generated
   */
  void setGenre(GenreTypes value);

  /**
   * Unsets the value of the '{@link org.eclipse.emf.test.models.movie.db.MovieType#getGenre <em>Genre</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetGenre()
   * @see #getGenre()
   * @see #setGenre(GenreTypes)
   * @generated
   */
  void unsetGenre();

  /**
   * Returns whether the value of the '{@link org.eclipse.emf.test.models.movie.db.MovieType#getGenre <em>Genre</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Genre</em>' attribute is set.
   * @see #unsetGenre()
   * @see #getGenre()
   * @see #setGenre(GenreTypes)
   * @generated
   */
  boolean isSetGenre();

  /**
   * Returns the value of the '<em><b>Summary</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Summary</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Summary</em>' attribute.
   * @see #setSummary(String)
   * @see org.eclipse.emf.test.models.movie.db.DbPackage#getMovieType_Summary()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   * @generated
   */
  String getSummary();

  /**
   * Sets the value of the '{@link org.eclipse.emf.test.models.movie.db.MovieType#getSummary <em>Summary</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Summary</em>' attribute.
   * @see #getSummary()
   * @generated
   */
  void setSummary(String value);

  /**
   * Returns the value of the '<em><b>Critics Review Group</b></em>' attribute list.
   * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Critics Review Group</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Critics Review Group</em>' attribute list.
   * @see org.eclipse.emf.test.models.movie.db.DbPackage#getMovieType_CriticsReviewGroup()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   * @generated
   */
  FeatureMap getCriticsReviewGroup();

  /**
   * Returns the value of the '<em><b>Critics Review</b></em>' containment reference list.
   * The list contents are of type {@link org.eclipse.emf.test.models.movie.db.CriticsReviewType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Critics Review</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Critics Review</em>' containment reference list.
   * @see org.eclipse.emf.test.models.movie.db.DbPackage#getMovieType_CriticsReview()
   * @model type="org.eclipse.emf.test.models.movie.db.CriticsReviewType" containment="true" resolveProxies="false" transient="true" volatile="true" derived="true"
   * @generated
   */
  EList getCriticsReview();

  /**
   * Returns the value of the '<em><b>Checked Out By</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Checked Out By</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Checked Out By</em>' reference.
   * @see #setCheckedOutBy(CustomerType)
   * @see org.eclipse.emf.test.models.movie.db.DbPackage#getMovieType_CheckedOutBy()
   * @model required="true"
   * @generated
   */
  CustomerType getCheckedOutBy();

  /**
   * Sets the value of the '{@link org.eclipse.emf.test.models.movie.db.MovieType#getCheckedOutBy <em>Checked Out By</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Checked Out By</em>' reference.
   * @see #getCheckedOutBy()
   * @generated
   */
  void setCheckedOutBy(CustomerType value);

  /**
   * Returns the value of the '<em><b>Any</b></em>' attribute list.
   * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Any</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Any</em>' attribute list.
   * @see org.eclipse.emf.test.models.movie.db.DbPackage#getMovieType_Any()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry"
   * @generated
   */
  FeatureMap getAny();

  /**
   * Returns the value of the '<em><b>ID</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>ID</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>ID</em>' attribute.
   * @see #setID(String)
   * @see org.eclipse.emf.test.models.movie.db.DbPackage#getMovieType_ID()
   * @model unique="false" id="true" dataType="org.eclipse.emf.ecore.xml.type.ID" required="true"
   * @generated
   */
  String getID();

  /**
   * Sets the value of the '{@link org.eclipse.emf.test.models.movie.db.MovieType#getID <em>ID</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>ID</em>' attribute.
   * @see #getID()
   * @generated
   */
  void setID(String value);

} // MovieType