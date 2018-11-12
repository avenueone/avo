import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVesselAttribute, defaultValue } from 'app/shared/model/vessel-attribute.model';

export const ACTION_TYPES = {
  FETCH_VESSELATTRIBUTE_LIST: 'vesselAttribute/FETCH_VESSELATTRIBUTE_LIST',
  FETCH_VESSELATTRIBUTE: 'vesselAttribute/FETCH_VESSELATTRIBUTE',
  CREATE_VESSELATTRIBUTE: 'vesselAttribute/CREATE_VESSELATTRIBUTE',
  UPDATE_VESSELATTRIBUTE: 'vesselAttribute/UPDATE_VESSELATTRIBUTE',
  DELETE_VESSELATTRIBUTE: 'vesselAttribute/DELETE_VESSELATTRIBUTE',
  RESET: 'vesselAttribute/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVesselAttribute>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type VesselAttributeState = Readonly<typeof initialState>;

// Reducer

export default (state: VesselAttributeState = initialState, action): VesselAttributeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VESSELATTRIBUTE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VESSELATTRIBUTE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_VESSELATTRIBUTE):
    case REQUEST(ACTION_TYPES.UPDATE_VESSELATTRIBUTE):
    case REQUEST(ACTION_TYPES.DELETE_VESSELATTRIBUTE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_VESSELATTRIBUTE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VESSELATTRIBUTE):
    case FAILURE(ACTION_TYPES.CREATE_VESSELATTRIBUTE):
    case FAILURE(ACTION_TYPES.UPDATE_VESSELATTRIBUTE):
    case FAILURE(ACTION_TYPES.DELETE_VESSELATTRIBUTE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_VESSELATTRIBUTE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_VESSELATTRIBUTE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_VESSELATTRIBUTE):
    case SUCCESS(ACTION_TYPES.UPDATE_VESSELATTRIBUTE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_VESSELATTRIBUTE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/vessel-attributes';

// Actions

export const getEntities: ICrudGetAllAction<IVesselAttribute> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_VESSELATTRIBUTE_LIST,
    payload: axios.get<IVesselAttribute>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IVesselAttribute> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VESSELATTRIBUTE,
    payload: axios.get<IVesselAttribute>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IVesselAttribute> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VESSELATTRIBUTE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVesselAttribute> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VESSELATTRIBUTE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVesselAttribute> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VESSELATTRIBUTE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
