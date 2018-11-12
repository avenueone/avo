import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVesselType, defaultValue } from 'app/shared/model/vessel-type.model';

export const ACTION_TYPES = {
  FETCH_VESSELTYPE_LIST: 'vesselType/FETCH_VESSELTYPE_LIST',
  FETCH_VESSELTYPE: 'vesselType/FETCH_VESSELTYPE',
  CREATE_VESSELTYPE: 'vesselType/CREATE_VESSELTYPE',
  UPDATE_VESSELTYPE: 'vesselType/UPDATE_VESSELTYPE',
  DELETE_VESSELTYPE: 'vesselType/DELETE_VESSELTYPE',
  RESET: 'vesselType/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVesselType>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type VesselTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: VesselTypeState = initialState, action): VesselTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VESSELTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VESSELTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_VESSELTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_VESSELTYPE):
    case REQUEST(ACTION_TYPES.DELETE_VESSELTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_VESSELTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VESSELTYPE):
    case FAILURE(ACTION_TYPES.CREATE_VESSELTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_VESSELTYPE):
    case FAILURE(ACTION_TYPES.DELETE_VESSELTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_VESSELTYPE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_VESSELTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_VESSELTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_VESSELTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_VESSELTYPE):
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

const apiUrl = 'api/vessel-types';

// Actions

export const getEntities: ICrudGetAllAction<IVesselType> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_VESSELTYPE_LIST,
    payload: axios.get<IVesselType>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IVesselType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VESSELTYPE,
    payload: axios.get<IVesselType>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IVesselType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VESSELTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVesselType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VESSELTYPE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVesselType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VESSELTYPE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
