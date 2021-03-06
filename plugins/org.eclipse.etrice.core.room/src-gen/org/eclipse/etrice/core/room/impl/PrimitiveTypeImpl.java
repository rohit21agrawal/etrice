/**
 */
package org.eclipse.etrice.core.room.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.etrice.core.room.LiteralType;
import org.eclipse.etrice.core.room.PrimitiveType;
import org.eclipse.etrice.core.room.RoomPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Primitive Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.etrice.core.room.impl.PrimitiveTypeImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.etrice.core.room.impl.PrimitiveTypeImpl#getTargetName <em>Target Name</em>}</li>
 *   <li>{@link org.eclipse.etrice.core.room.impl.PrimitiveTypeImpl#getCastName <em>Cast Name</em>}</li>
 *   <li>{@link org.eclipse.etrice.core.room.impl.PrimitiveTypeImpl#getDefaultValueLiteral <em>Default Value Literal</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PrimitiveTypeImpl extends DataTypeImpl implements PrimitiveType
{
  /**
   * The default value of the '{@link #getType() <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected static final LiteralType TYPE_EDEFAULT = LiteralType.BOOL;

  /**
   * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected LiteralType type = TYPE_EDEFAULT;

  /**
   * The default value of the '{@link #getTargetName() <em>Target Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTargetName()
   * @generated
   * @ordered
   */
  protected static final String TARGET_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getTargetName() <em>Target Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTargetName()
   * @generated
   * @ordered
   */
  protected String targetName = TARGET_NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getCastName() <em>Cast Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCastName()
   * @generated
   * @ordered
   */
  protected static final String CAST_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCastName() <em>Cast Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCastName()
   * @generated
   * @ordered
   */
  protected String castName = CAST_NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getDefaultValueLiteral() <em>Default Value Literal</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDefaultValueLiteral()
   * @generated
   * @ordered
   */
  protected static final String DEFAULT_VALUE_LITERAL_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDefaultValueLiteral() <em>Default Value Literal</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDefaultValueLiteral()
   * @generated
   * @ordered
   */
  protected String defaultValueLiteral = DEFAULT_VALUE_LITERAL_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PrimitiveTypeImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return RoomPackage.Literals.PRIMITIVE_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LiteralType getType()
  {
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setType(LiteralType newType)
  {
    LiteralType oldType = type;
    type = newType == null ? TYPE_EDEFAULT : newType;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RoomPackage.PRIMITIVE_TYPE__TYPE, oldType, type));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getTargetName()
  {
    return targetName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTargetName(String newTargetName)
  {
    String oldTargetName = targetName;
    targetName = newTargetName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RoomPackage.PRIMITIVE_TYPE__TARGET_NAME, oldTargetName, targetName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getCastName()
  {
    return castName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCastName(String newCastName)
  {
    String oldCastName = castName;
    castName = newCastName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RoomPackage.PRIMITIVE_TYPE__CAST_NAME, oldCastName, castName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getDefaultValueLiteral()
  {
    return defaultValueLiteral;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDefaultValueLiteral(String newDefaultValueLiteral)
  {
    String oldDefaultValueLiteral = defaultValueLiteral;
    defaultValueLiteral = newDefaultValueLiteral;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RoomPackage.PRIMITIVE_TYPE__DEFAULT_VALUE_LITERAL, oldDefaultValueLiteral, defaultValueLiteral));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case RoomPackage.PRIMITIVE_TYPE__TYPE:
        return getType();
      case RoomPackage.PRIMITIVE_TYPE__TARGET_NAME:
        return getTargetName();
      case RoomPackage.PRIMITIVE_TYPE__CAST_NAME:
        return getCastName();
      case RoomPackage.PRIMITIVE_TYPE__DEFAULT_VALUE_LITERAL:
        return getDefaultValueLiteral();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case RoomPackage.PRIMITIVE_TYPE__TYPE:
        setType((LiteralType)newValue);
        return;
      case RoomPackage.PRIMITIVE_TYPE__TARGET_NAME:
        setTargetName((String)newValue);
        return;
      case RoomPackage.PRIMITIVE_TYPE__CAST_NAME:
        setCastName((String)newValue);
        return;
      case RoomPackage.PRIMITIVE_TYPE__DEFAULT_VALUE_LITERAL:
        setDefaultValueLiteral((String)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case RoomPackage.PRIMITIVE_TYPE__TYPE:
        setType(TYPE_EDEFAULT);
        return;
      case RoomPackage.PRIMITIVE_TYPE__TARGET_NAME:
        setTargetName(TARGET_NAME_EDEFAULT);
        return;
      case RoomPackage.PRIMITIVE_TYPE__CAST_NAME:
        setCastName(CAST_NAME_EDEFAULT);
        return;
      case RoomPackage.PRIMITIVE_TYPE__DEFAULT_VALUE_LITERAL:
        setDefaultValueLiteral(DEFAULT_VALUE_LITERAL_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case RoomPackage.PRIMITIVE_TYPE__TYPE:
        return type != TYPE_EDEFAULT;
      case RoomPackage.PRIMITIVE_TYPE__TARGET_NAME:
        return TARGET_NAME_EDEFAULT == null ? targetName != null : !TARGET_NAME_EDEFAULT.equals(targetName);
      case RoomPackage.PRIMITIVE_TYPE__CAST_NAME:
        return CAST_NAME_EDEFAULT == null ? castName != null : !CAST_NAME_EDEFAULT.equals(castName);
      case RoomPackage.PRIMITIVE_TYPE__DEFAULT_VALUE_LITERAL:
        return DEFAULT_VALUE_LITERAL_EDEFAULT == null ? defaultValueLiteral != null : !DEFAULT_VALUE_LITERAL_EDEFAULT.equals(defaultValueLiteral);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (type: ");
    result.append(type);
    result.append(", targetName: ");
    result.append(targetName);
    result.append(", castName: ");
    result.append(castName);
    result.append(", defaultValueLiteral: ");
    result.append(defaultValueLiteral);
    result.append(')');
    return result.toString();
  }

} //PrimitiveTypeImpl
